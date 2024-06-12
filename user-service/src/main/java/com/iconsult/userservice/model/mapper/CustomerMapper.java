package com.iconsult.userservice.model.mapper;

import com.iconsult.userservice.model.dto.request.CustomerDto;
import com.iconsult.userservice.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper
{
    Customer dtoToJpe(CustomerDto customerDto);
}
