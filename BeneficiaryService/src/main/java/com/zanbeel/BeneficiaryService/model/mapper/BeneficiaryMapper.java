package com.zanbeel.BeneficiaryService.model.mapper;

import com.zanbeel.BeneficiaryService.model.dto.request.BeneficiaryRequestDto;
import com.zanbeel.BeneficiaryService.model.dto.response.BeneficiaryResponseDto;
import com.zanbeel.BeneficiaryService.model.entity.Beneficiary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface

BeneficiaryMapper {
    BeneficiaryMapper INSTANCE = Mappers.getMapper(BeneficiaryMapper.class);

    @Mapping(target = "beneficiaryId", ignore = true)
    @Mapping(target = "isActive", source = "isActive")
    Beneficiary mapBeneficiaryRequestDtoToBeneficiary(BeneficiaryRequestDto beneficiaryRequestDto, Boolean isActive);

    BeneficiaryResponseDto mapBeneficiaryToBeneficiaryResponseDto(Beneficiary beneficiary);
    @Mapping(target = "beneficiaryId", source = "id")
    Beneficiary mapBeneficiaryRequestDtoToBeneficiary(Long id, BeneficiaryRequestDto beneficiaryRequestDto);

}
