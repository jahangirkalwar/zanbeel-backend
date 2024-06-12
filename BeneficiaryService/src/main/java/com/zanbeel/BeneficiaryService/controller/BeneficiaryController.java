    package com.zanbeel.BeneficiaryService.controller;


    import com.zanbeel.BeneficiaryService.model.dto.request.BeneficiaryRequestDto;
    import com.zanbeel.BeneficiaryService.model.dto.request.OtpRequestDto;
    import com.zanbeel.BeneficiaryService.model.dto.request.UpdateBeneficiaryRequestDto;
    import com.zanbeel.BeneficiaryService.model.dto.request.ValidatedOtpRequestDto;
    import com.zanbeel.BeneficiaryService.model.dto.response.BeneficiaryResponseDto;
    import com.zanbeel.BeneficiaryService.model.entity.Beneficiary;
    import com.zanbeel.BeneficiaryService.model.entity.Otp;
    import com.zanbeel.BeneficiaryService.service.BeneficiaryService;
    import com.zanbeel.BeneficiaryService.service.OtpService;
    import com.zanbeel.customUtility.exception.GlobalExceptionHandler;
    import com.zanbeel.customUtility.model.CustomResponseEntity;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping(value = "/v1/beneficiary")
    public class BeneficiaryController extends GlobalExceptionHandler {
        @Autowired
        private BeneficiaryService beneficiaryService;
        @Autowired
        private OtpService otpService;

        @PostMapping
        public CustomResponseEntity<OtpRequestDto> addBeneficiary(@RequestBody BeneficiaryRequestDto beneficiaryRequestDto) {
            return beneficiaryService.addBeneficiary(beneficiaryRequestDto);
        }
        @PutMapping(value = "/{beneficiaryId}")
        public CustomResponseEntity<Beneficiary> updateBeneficiary(@PathVariable Long beneficiaryId,
                                                                   @RequestBody UpdateBeneficiaryRequestDto updateBeneficiaryRequestDto) {
            return beneficiaryService.updateBeneficiary(beneficiaryId, updateBeneficiaryRequestDto);
        }

        @GetMapping(value = "getBeneficiary/{beneficiaryId}")
        public CustomResponseEntity<BeneficiaryResponseDto> getBeneficiaryById(@PathVariable Long beneficiaryId) {
            return beneficiaryService.getBeneficiaryById(beneficiaryId);
        }

        @GetMapping(value = "/getAllBeneficiary/{customerId}")
        public CustomResponseEntity<List<BeneficiaryResponseDto>> getAllBeneficiaryByCustomerId(@PathVariable Long customerId) {
            return beneficiaryService.getAllBeneficiaryByCustomerId(customerId);
        }
        @DeleteMapping(value = "/{beneficiaryId}")
        public CustomResponseEntity<String> deleteBeneficiaryById(@PathVariable Long beneficiaryId) {
            return beneficiaryService.deleteBeneficiaryById(beneficiaryId);
        }

        @PostMapping("/generateOtp")
        public CustomResponseEntity<Otp> createOtp(@RequestBody OtpRequestDto otpRequestDto) {
            return otpService.addOtp(otpRequestDto);
        }

        @PostMapping("/verifyOtp")
        public CustomResponseEntity<String> verifyOtp(@RequestBody ValidatedOtpRequestDto otpRequestDto) {
            return otpService.verifyOtp(otpRequestDto);
        }

    }
