package com.iconsult.userservice.service.Impl;

import com.iconsult.userservice.Util.Util;
import com.iconsult.userservice.enums.CustomerStatus;
import com.iconsult.userservice.enums.ResponseCodes;
import com.iconsult.userservice.enums.SMSCategory;
import com.iconsult.userservice.exception.ServiceException;
import com.iconsult.userservice.model.dto.request.*;
import com.iconsult.userservice.model.dto.response.AccountDto;
import com.iconsult.userservice.model.dto.response.KafkaMessageDto;
import com.iconsult.userservice.model.entity.AppConfiguration;
import com.iconsult.userservice.model.entity.Customer;
import com.iconsult.userservice.model.mapper.CustomerMapper;
import com.iconsult.userservice.model.mapper.CustomerMapperImpl;
import com.iconsult.userservice.repository.CustomerRepository;
import com.iconsult.userservice.service.CustomerService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.zanbeel.customUtility.model.CustomResponseEntity;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import javax.ws.rs.core.MediaType;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class CustomerServiceImpl implements CustomerService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    //private final String URL = "http://192.168.0.196:8085/account/getAccounts?cnicNumber=%s";
//    private final String URL = "http://localhost:8081/customer/getDetails?cnicNumber=%s&";
    private final String URL = "http://localhost:8081/customer/getDetails?cnicNumber=%s&email=%s&accountNumber=%s";
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    private KafkaMessageDto kafkaMessage;

    private CustomResponseEntity response;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapperImpl customerMapperImpl;

    @Autowired
    private AppConfigurationImpl appConfigurationImpl;

//    @Override
//    public CustomResponseEntity register(CustomerDto customerDto)
//    {
//        LOGGER.info("Sign up Request received");
//
//        // Duplicate Customer Check mobile number
//        Customer customerDuplicate = findByMobileNumber(customerDto.getMobileNumber());
//
//        if(customerDuplicate != null)
//        {
//            LOGGER.error("Customer already exists with mobile [" + customerDto.getMobileNumber() + "], cannot allow signup, rejecting...");
//            throw new ServiceException(String.format("Customer with Mobile Number %s already exists", customerDto.getMobileNumber()));
//        }
//
//        // Duplicate Customer Check if email is present
//        if(!customerDto.getEmail().isBlank())
//        {
//            customerDuplicate = findByEmailAddress(customerDto.getEmail());
//            if(customerDuplicate != null)
//            {
//                LOGGER.error("Customer already exists with Email [" + customerDto.getEmail() + "], cannot allow signup, rejecting...");
//                throw new ServiceException(String.format("Customer with Email %s already exists", customerDto.getEmail()));
//            }
//        }
//
//        // Duplicate Customer Check username
//        customerDuplicate = findByUserName(customerDto.getUserName());
//
//        if(customerDuplicate != null)
//        {
//            LOGGER.error("Customer already exists with userName [" + customerDto.getUserName() + "], cannot allow signup, rejecting...");
//            throw new ServiceException(String.format("Customer with userName %s already exists", customerDto.getUserName()));
//        }
//
//        Customer customer = addUser(customerMapperImpl.dtoToJpe(customerDto));
//
//        LOGGER.info("Customer has been saved with Id {}", customer.getId());
//        Map<String,Object> result = new HashMap<>();
//        result.put("mobileNumber",customer.getMobileNumber());
//        result.put("customerId",customer.getId());
//
//        return response;
//    }

    @Override
    public CustomResponseEntity register(CustomerDto customerDto, OTPLogImpl otpLogImpl)
    {
        LOGGER.info("Sign up Request received");

        if(!accountExist(customerDto.getCnic(), customerDto.getEmail(), customerDto.getAccountNumber()))
        {
            LOGGER.error("Customer account does not exist [" + customerDto.getCnic() + "], cannot allow signup, rejecting...");
            throw new ServiceException(String.format("Customer account [%s] does not exist", customerDto.getCnic()));
        }

        // Duplicate Customer Check mobile number
        Customer customerDuplicate = findByMobileNumber(customerDto.getMobileNumber());

        if(customerDuplicate != null)
        {
            LOGGER.error("Customer already exists with mobile [" + customerDto.getMobileNumber() + "], cannot allow signup, rejecting...");
            throw new ServiceException(String.format("Customer with Mobile Number %s already exists", customerDto.getMobileNumber()));
        }

        // Duplicate Customer Check if email is present
        if(!customerDto.getEmail().isBlank())
        {
            customerDuplicate = findByEmailAddress(customerDto.getEmail());
            if(customerDuplicate != null)
            {
                LOGGER.error("Customer already exists with Email [" + customerDto.getEmail() + "], cannot allow signup, rejecting...");
                throw new ServiceException(String.format("Customer with Email %s already exists", customerDto.getEmail()));
            }
        }

        // Duplicate Customer Check username
        customerDuplicate = findByUserName(customerDto.getUserName());

        if(customerDuplicate != null)
        {
            LOGGER.error("Customer already exists with userName [" + customerDto.getUserName() + "], cannot allow signup, rejecting...");
            throw new ServiceException(String.format("Customer with userName %s already exists", customerDto.getUserName()));
        }

//        Customer customer = customerMapperImpl.dtoToJpe(customerDto);
//        customer.setStatus(CustomerStatus.TEMP_BLOCK.getCode());
//        addUser(customer);

        //LOGGER.info("Customer has been saved with Id {}", customer.getId());

        if(!otpLogImpl.createAndSendOTP(new OTPDto(customerDto.getMobileNumber(), customerDto.getEmail(), SMSCategory.VERIFY_MOBILE_DEVICE.getValue()))) // sending OTP after register
        {
            LOGGER.error("Failed to create & Send OTP for Mobile [" + customerDto.getMobileNumber() + "], rejecting...");
            throw new ServiceException("SMS Gateway Down");
        }

//        Map<String,Object> result = new HashMap<>();
//        result.put("mobileNumber",customer.getMobileNumber());
//        result.put("customerId",customer.getId());

        response = new CustomResponseEntity<>("OTP sent Successfully");

        return response;
    }

//    @Override
//    public CustomResponseEntity signup(CustomerDto customerDto, OTPLogImpl otpLogImpl) {
//        Customer customer = customerMapperImpl.dtoToJpe(customerDto);
//
//        if(!otpLogImpl.createAndSendOTP(new OTPDto(customerDto.getEmail()))){
//            LOGGER.error("Failed to create & Send OTP to email [" + customerDto.getEmail() + "], rejecting...");
//            throw new ServiceException("SMS Gateway Down");
//        }
//        customerRepository.save(customer);
//        return new CustomResponseEntity<>("customer saved succesfully");
//    }

    @Override
    public CustomResponseEntity signup(CustomerDto customerDto, OTPLogImpl otpLogImpl) {
        // Create a customer entity from the DTO
        Customer customer = customerMapperImpl.dtoToJpe(customerDto);

        // Verify OTP
        if (!otpLogImpl.verifyOTP2(new OTPDto(customerDto.getMobileNumber(), customerDto.getEmail(), SMSCategory.VERIFY_MOBILE_DEVICE.getValue(), customerDto.getOTP()))) {
            LOGGER.error("Failed to verify OTP, rejecting...");
            throw new ServiceException("Invalid OTP");
        }
        // Save the customer entity after OTP verification
        customerRepository.save(customer);

        return new CustomResponseEntity<>("Customer Registered successfully");
    }

    @Override
    public CustomResponseEntity login(LoginDto loginDto)
    {
        Customer customer = customerRepository.findByEmailOrUserName(loginDto.getEmailorUsername());

        if(customer != null)
        {
            if(!customer.getStatus().equals(CustomerStatus.ACTIVE.getCode())) // customer not active
            {
                response = new CustomResponseEntity<>(ResponseCodes.CUSTOMER_INACTIVE.getCode(), ResponseCodes.CUSTOMER_INACTIVE.getValue());
                return response;
            }

            if (customer.getPassword().equals(loginDto.getPassword()) &&
                    (loginDto.getSecurityImage() == null || customer.getSecurityPicture().equals(loginDto.getSecurityImage())))
            {
                Map<String,Object> data = new HashMap<>();
                data.put("customerId", customer.getId());
                response = new CustomResponseEntity<>(data, "customer logged in successfully");
                return response;
            }
            else
            {
                throw new ServiceException("Invalid Password or Security Image");
            }
        }

        throw new ServiceException("Customer does not exist");
    }

    @Override
    public CustomResponseEntity verifyCNIC(String cnic , String email , String accountNumber)
    {
        LOGGER.info("Verify CNIC request received");

        if(!accountExist(cnic , email , accountNumber))
        {
            LOGGER.error("Customer account does not exist [" + cnic + "], cannot allow signup, rejecting...");
            throw new ServiceException(String.format("Customer account does not exists", cnic));
        }
        return new CustomResponseEntity<>("Customer Account Exists, allowing sign up");
    }

    @Override
    public Customer addUser(Customer customer)
    {
        return save(customer);
    }

    @Override
    public void deleteUser(Long id) {
        this.customerRepository.deleteById(id);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Override
    public Customer save(Customer customer)
    {
        return customerRepository.save(customer);
    }

    @Override
    public CustomResponseEntity<Customer> findById(Long id) {
        Optional<Customer> customer = this.customerRepository.findById(id);
        if (!customer.isPresent()) {
            return new CustomResponseEntity<>(1000, "Customer Not Found");
        }
        return new CustomResponseEntity<>(customer.get(), "Data Found");
    }

    @Override
    public CustomResponseEntity forgetUserName(ForgetUsernameDto forgetUsernameDto)
    {
        LOGGER.info("ForgetUsername Request Received...");

        Customer customer = findByEmailAddress(forgetUsernameDto.getEmail());

        if(customer != null)
        {
            LOGGER.info("Customer found with Email Address [{}], sending username on email...", forgetUsernameDto.getEmail());
            // Kafka email send here
            kafkaMessage = new KafkaMessageDto(forgetUsernameDto.getEmail(), "Forget Username", "Dear Customer, your username is " + customer.getUserName(), true, false);
            sendMessage(kafkaMessage, "forgetUserName");

            LOGGER.info("Email sent [{}]", forgetUsernameDto.getEmail());
            response = new CustomResponseEntity<>("user name sent successfully");
            return response;
        }

        LOGGER.info("Customer Email does not exists, verifying mobile number...");

        customer = findByMobileNumber(forgetUsernameDto.getMobileNumber());

        if(customer != null)
        {
            LOGGER.info("Customer found with Mobile Number [{}], sending username on SMS...", forgetUsernameDto.getMobileNumber());
            // Kafka SMS send here
            kafkaMessage = new KafkaMessageDto(forgetUsernameDto.getMobileNumber(), "UserName", "Dear Customer, your username is " + customer.getUserName(), false, true);
            sendMessage(kafkaMessage, "forgetUserName");

            LOGGER.info("SMS sent [{}]", forgetUsernameDto.getMobileNumber());
            response = new CustomResponseEntity<>("user name sent successfully");
            return response;
        }

        throw new ServiceException("Customer Not Found");
    }

    @Override
    public CustomResponseEntity forgetPassword(ForgetUsernameDto forgetUsernameDto)
    {
        LOGGER.info("ForgetPassword request received");
        try
        {
            // Lookup customer in database by e-mail
            Customer customer = findByEmailAddress(forgetUsernameDto.getEmail());

            if(customer == null)
            {
                LOGGER.error("Customer with Email [{}] does not exist", forgetUsernameDto.getEmail());
                throw new ServiceException("Customer not found!!");
            }

            // Generate random 36-character string token for reset password
            customer.setResetToken(UUID.randomUUID().toString());
            AppConfiguration appConfiguration = this.appConfigurationImpl.findByName("RESET_EXPIRE_TIME"); // fetching token expire time in minutes

            // Generate reset token expire time for reset password
            customer.setResetTokenExpireTime(Long.parseLong(Util.dateFormat.format(DateUtils.addMinutes(new Date(), Integer.parseInt(appConfiguration.getValue())))));

            // Save token to database
            save(customer);

            String resetAppUrl = "http://192.168.0.196:9094/v1/customer/verifyForgetPasswordToken?token=" + customer.getResetToken();

            // Email message
            kafkaMessage = new KafkaMessageDto(forgetUsernameDto.getEmail(), "Forget Password", "Dear Customer, To reset your password, click the link below:\n" + resetAppUrl, true, false);
            sendMessage(kafkaMessage, "forgetUserName");

            LOGGER.info("Email sent [{}]", forgetUsernameDto.getEmail());
            response = new CustomResponseEntity<>("user name sent successfully");
            return response;
        }
        catch (Exception e)
        {
            LOGGER.error("Password reset link send failed...");
            LOGGER.error(e.getMessage());
            throw new ServiceException("Oops! Password reset link send failed...");
        }
    }

    @Override
    public CustomResponseEntity verifyResetPasswordToken(String token)
    {
        LOGGER.info("VerifyResetPasswordToken Request Received...");
        LOGGER.info("Verifying ResetToken [{}]", token);

        try
        {
            Customer customer = findByResetToken(token);

            if(customer != null) // Token found in DB
            {
                if(customer.getResetTokenExpireTime() > Long.parseLong(Util.dateFormat.format(new Date())))
                {
                    LOGGER.info("Customer ResetPassword token found and valid for customer [{}]...", customer.getMobileNumber());
                    response = new CustomResponseEntity<>(token,"Token found and valid");
                    return response;
                }
                else
                {
                    LOGGER.error("Reset Token [{}] has been expired for customer [{}], replying...", customer.getResetToken(), customer.getMobileNumber());
                    throw new ServiceException("Oops! This is an invalid password reset link.");
                }
            }

            throw new ServiceException("Oops! This is an invalid password reset link.");
        }
        catch (Exception e)
        {
            LOGGER.error("Password reset failed...");
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public CustomResponseEntity resetPassword(ResetPasswordDto resetPasswordDto)
    {
        LOGGER.info("ResetPassword Request Received...");

        try
        {
            Customer resetCustomerPassword = findByResetToken(resetPasswordDto.getToken()); // Find the user associated with the reset token

            if(resetCustomerPassword != null) // This should always be non-null, but we check just in case
            {
                resetCustomerPassword.setPassword(resetPasswordDto.getPassword()); // Set new password
                resetCustomerPassword.setResetToken(null); // Set the reset token to null, so it cannot be used again
                save(resetCustomerPassword); // Save customer

                LOGGER.info("Password reset successful for customer [{}]", resetCustomerPassword.getMobileNumber());
                response = new CustomResponseEntity<>("You have successfully reset your password. You may now login.");
                return response;
            }

            LOGGER.error("Password reset failed...");
            throw new ServiceException("Oops! This is an invalid password reset link.");
        }
        catch (Exception e)
        {
            LOGGER.error("Password reset failed...");
            LOGGER.error(e.getMessage());
            throw new ServiceException("Oops! Password reset failed..");
        }
    }

    @Override
    public Customer findByResetToken(String resetToken)
    {
        return this.customerRepository.findByResetToken(resetToken);
    }

    @Override
    public Customer findByEmailAddress(String email)
    {
        return this.customerRepository.findByEmail(email);
    }

    @Override
    public Customer findByMobileNumber(String mobileNumber) {
        return this.customerRepository.findByMobileNumber(mobileNumber);
    }

    @Override
    public Customer findByUserName(String userName) {
        return this.customerRepository.findByUserName(userName);
    }

    public List<Customer> findAllUsers()
    {
        return this.customerRepository.findAll();
    }

    public Customer updateCustomer(CustomerDto customerDto)
    {

        Customer updateCustomer = this.customerRepository.findByEmail(customerDto.getEmail());

        if(updateCustomer == null)
        {
            throw new ServiceException(String.format("User with email %s not found", customerDto.getEmail()));
        }

        updateCustomer.setFirstName(customerDto.getFirstName());
        updateCustomer.setLastName(customerDto.getLastName());
        updateCustomer.setEmail(customerDto.getEmail());

        save(updateCustomer);
        LOGGER.info("Customer has been updated with Id {}", updateCustomer.getId());

        return ResponseEntity.ok(updateCustomer).getBody();
    }

    private Boolean accountExist(String cnic , String email , String accountNumber)
    {
        Client client = Client.create();
        client.setConnectTimeout(5 * 1000);
        client.setReadTimeout(5 * 1000);
        WebResource webResource = null;

        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            String url = String.format(URL, cnic , email , accountNumber);
            LOGGER.info("Calling URL [" + url + "]");
            webResource = client.resource(url);

            ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if(clientResponse != null)
            {
                if(clientResponse.getStatus() != 302)
                {
                    LOGGER.error("Account Service down, rejecting!!");
                    return false;
                }
                AccountDto accountList = clientResponse.getEntity(new GenericType<AccountDto>() {});

                if(accountList != null && !accountList.getAccountList().isEmpty() )
                {
                    LOGGER.info("Account Service response found For Customer [{}], proceeding...", cnic);
                    return true;
                }
            }
            else
            {
                LOGGER.error("Failed to Get response from Account Service For Customer [{}], rejecting...", cnic);
            }
            return false;
        }
        catch (Exception e)
        {
            LOGGER.error("Failed to Get response from Account Service For Customer [{}], rejecting...", cnic);
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public void sendMessage(Object message, String topicName)
    {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.info("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                LOGGER.error("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }

    public boolean setCustomerStatus(String email, String mobileNumber)
    {
        Customer customer = findByEmailAddress(email);
        if(customer != null)
        {
            customer.setStatus(CustomerStatus.ACTIVE.getCode());
            save(customer);
            LOGGER.info("Customer with [{}] Status has been updated successfully", email);
            return true;
        }

        LOGGER.error("No Customer found to update the status");
        return false;
    }
}