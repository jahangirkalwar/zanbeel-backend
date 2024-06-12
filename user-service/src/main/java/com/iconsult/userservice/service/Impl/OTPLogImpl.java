package com.iconsult.userservice.service.Impl;

import com.iconsult.userservice.Util.Util;
import com.iconsult.userservice.enums.SMSCategory;
import com.iconsult.userservice.exception.ServiceException;
import com.iconsult.userservice.model.dto.request.OTPDto;
import com.iconsult.userservice.model.dto.response.KafkaMessageDto;
import com.iconsult.userservice.model.dto.response.ResponseDTO;
import com.iconsult.userservice.model.entity.AppConfiguration;
import com.iconsult.userservice.model.entity.Customer;
import com.iconsult.userservice.model.entity.OTPLog;
import com.iconsult.userservice.repository.OTPLogRepository;
import com.iconsult.userservice.service.OTPLogSerivce;
import com.zanbeel.customUtility.model.CustomResponseEntity;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Service
public class OTPLogImpl implements OTPLogSerivce {

    private static final Logger LOGGER = LoggerFactory.getLogger(OTPLogImpl.class);

    private CustomResponseEntity response;

    @Autowired
    private OTPLogRepository otpLogRepository;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private AppConfigurationImpl appConfigurationImpl;

    private KafkaMessageDto kafkaMessage;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Override
    public OTPLog save(OTPLog otpLog) {
        return this.otpLogRepository.save(otpLog);
    }

    @Override
    public List<OTPLog> findByMobileNumberAndIsExpired(String mobileNumber, Boolean isExpired) {
        return this.otpLogRepository.findByMobileNumberAndIsExpired(mobileNumber, isExpired);
    }

    @Override
    public CustomResponseEntity createOTP(OTPDto OTPDto)
    {
        LOGGER.info("Executing createOTP Request...");

//        Customer customer = this.customerServiceImpl.findByMobileNumber(OTPDto.getMobileNumber());
//
//        if(customer != null)
//        {
//            LOGGER.error("Customer already exists with mobile [" + OTPDto.getMobileNumber() + "], cannot allow signup, rejecting...");
//            throw new ServiceException(String.format("User with Mobile Number %s already exists", OTPDto.getMobileNumber()));
//        }

        // making all OTP for this mobile number expire
        for (OTPLog otp : findByMobileNumberAndIsExpired(OTPDto.getMobileNumber(), false)) {
            otp.setIsExpired(true);
            otp.setIsVerified(false);
            save(otp);
        }

        if(!createAndSendOTP(OTPDto))
        {
            LOGGER.error("Failed to create & Send OTP for Mobile [" + OTPDto.getMobileNumber() + "], rejecting...");
            throw new ServiceException("SMS Gateway Down");
        }

        return response;
    }

    @Override
    public CustomResponseEntity verifyOTP(OTPDto verifyOTPDto)
    {
        LOGGER.info("Executing confirmOTP Request...");

        List<OTPLog> otpLogList = findByMobileNumberAndIsExpired(verifyOTPDto.getMobileNumber(), false);

        if(otpLogList != null && !otpLogList.isEmpty())
        {
            for(OTPLog otp : otpLogList)
            {
                if(otp.getExpiryDateTime() > Long.parseLong(Util.dateFormat.format(new Date())))
                {
                    if(otp.getOTP().equals(verifyOTPDto.getOtp()) && !otp.getIsVerified())
                    {
                        otp.setIsExpired(true);
                        otp.setVerifyDateTime(Long.parseLong(Util.dateFormat.format(new Date())));

                        if(otp.getReason().equals(SMSCategory.VERIFY_MOBILE_DEVICE.getValue()))
                        {
                           customerServiceImpl.setCustomerStatus(verifyOTPDto.getEmail(), verifyOTPDto.getMobileNumber()); // updating status of customer
                        }

                        if(save(otp).getId() != null)
                        {
                            LOGGER.info("OTP verified successfully for customer [{}], replying...", verifyOTPDto.getMobileNumber());
                            response = new CustomResponseEntity<>("verified otp successfully");
                            return response;
                        }
                    }
                    else
                    {
                        LOGGER.info("OTP does not match for customer [{}], replying...", verifyOTPDto.getMobileNumber());
                        response = new CustomResponseEntity<>(1013, "incorrect otp");
                        return response;
                    }
                }
                else
                {
                    LOGGER.info("OTP has been expired for customer [{}], replying...", verifyOTPDto.getMobileNumber());
                    response = new CustomResponseEntity<>(1013, "OTP has been expired ");
                    return response;
                }
            }

        }

        throw new ServiceException(String.format("No Otp found against mobilenumber [%s], rejecting...", verifyOTPDto.getMobileNumber()));
    }

    public Boolean createAndSendOTP(OTPDto OTPDto) {
        LOGGER.info("Generating OTP...");
        String otp = Util.generateOTP(6); // Generating OTP of length 6
        LOGGER.info("OTP Generated...");

        OTPLog otpLog = new OTPLog();
        otpLog.setMobileNumber(OTPDto.getMobileNumber());
        otpLog.setEmail(OTPDto.getEmail());
        otpLog.setOTP(otp);
        otpLog.setIsExpired(false);
        otpLog.setIsVerified(false);
        otpLog.setCreateDateTime(Long.parseLong(Util.dateFormat.format(new Date())));
        otpLog.setReason(OTPDto.getReason());
        AppConfiguration appConfiguration = this.appConfigurationImpl.findByName("OTP_EXPIRE_TIME"); // fetching otp expire time in minutes
        otpLog.setExpiryDateTime(Long.parseLong(Util.dateFormat.format(DateUtils.addMinutes(new Date(), Integer.parseInt(appConfiguration.getValue())))));
        otpLog.setSmsMessage("Dear Customer, your OTP to complete your request is " + otp);

        // Sending email with OTP
        try {
            // SMTP server properties
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true"); // If using TLS/SSL

            // Authenticator for SMTP server
            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("ahmedkalwar38@gmail.com", "rpulydzuxlmjsdyg");
                }
            };

            // Create session
            Session session = Session.getInstance(props, auth);

            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your_email_address"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(OTPDto.getEmail()));
            message.setSubject("OTP for your request");
            message.setText("Your OTP is: " + otp);

            // Send message
            Transport.send(message);

            LOGGER.info("Email sent successfully to [{}]", OTPDto.getEmail());

            // Save OTP log
            if (save(otpLog).getId() != null) {
                OTPDto.setOtp(otp);
                LOGGER.info("OTP has been saved with Id: {}", otpLog.getId());
                LOGGER.info("OTP Sent Successfully to [{}]", OTPDto.getMobileNumber());
                return true;
            }
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email to [{}]: {}", OTPDto.getEmail(), e.getMessage());
        }
        return false;
    }


    public Boolean verifyOTP2(OTPDto verifyOTPDto)
    {
        LOGGER.info("Executing confirmOTP Request...");

        List<OTPLog> otpLogList = findByMobileNumberAndIsExpired(verifyOTPDto.getMobileNumber(), false);

        if(otpLogList != null && !otpLogList.isEmpty())
        {
            for(OTPLog otp : otpLogList)
            {
                if(otp.getExpiryDateTime() > Long.parseLong(Util.dateFormat.format(new Date())))
                {
                    if(otp.getOTP().equals(verifyOTPDto.getOtp()) && !otp.getIsVerified())
                    {
                        otp.setIsExpired(true);
                        otp.setVerifyDateTime(Long.parseLong(Util.dateFormat.format(new Date())));

                        if(otp.getReason().equals(SMSCategory.VERIFY_MOBILE_DEVICE.getValue()))
                        {
                            customerServiceImpl.setCustomerStatus(verifyOTPDto.getEmail(), verifyOTPDto.getMobileNumber()); // updating status of customer
                        }

                        if(save(otp).getId() != null)
                        {
                            LOGGER.info("OTP verified successfully for customer [{}], replying...", verifyOTPDto.getMobileNumber());
                            response = new CustomResponseEntity<>("verified otp successfully");
                            return true;
                        }
                    }
                    else
                    {
                        LOGGER.info("OTP does not match for customer [{}], replying...", verifyOTPDto.getMobileNumber());
                        response = new CustomResponseEntity<>(1013, "incorrect otp");
                        return false;
                    }
                }
                else
                {
                    LOGGER.info("OTP has been expired for customer [{}], replying...", verifyOTPDto.getMobileNumber());
                    response = new CustomResponseEntity<>(1013, "OTP has been expired ");
                    return false;
                }
            }

        }

        return false;
    }

}