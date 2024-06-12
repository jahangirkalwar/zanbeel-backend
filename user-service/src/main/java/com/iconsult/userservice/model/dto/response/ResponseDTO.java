package com.iconsult.userservice.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ResponseDTO {

    private String message;
    private int statusCode;
    private Map<String, Object> additionalData;

    public ResponseDTO() {
        this.additionalData = new HashMap<>();
    }

    public ResponseDTO(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.additionalData = new HashMap<>();
    }

    public void addField(String fieldName, Object value) {
        additionalData.put(fieldName, value);
    }

}
