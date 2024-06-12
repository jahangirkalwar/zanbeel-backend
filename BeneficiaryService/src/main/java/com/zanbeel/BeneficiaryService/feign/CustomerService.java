package com.zanbeel.BeneficiaryService.feign;

import com.zanbeel.BeneficiaryService.model.dto.response.Customer;
import com.zanbeel.customUtility.model.CustomResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient("user-service")
@FeignClient(url = "http://localhost:8081", value = "user-service")
public interface CustomerService {

    @GetMapping(value = "/v1/customer/getCustomer/{id}")
    CustomResponseEntity<Customer> findById(@PathVariable Long id);
}
