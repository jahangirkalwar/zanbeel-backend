package com.zanbeel.BeneficiaryService.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zanbeel.customUtility.exception.ServiceException;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;

import java.io.IOException;

public class CustomErrorDecoder implements ErrorDecoder {
    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody = Util.toString(response.body().asReader());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        JsonNode errorsNode = jsonNode.get("errors");
        String firstError = errorsNode.get(0).asText();
        return new ServiceException(firstError);
    }

}
