package com.iconsult.userservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponseEntity<T> {
    private T data;
    private String error;

    public static <T> CustomResponseEntity<T> success(T data) {
        CustomResponseEntity<T> response = new CustomResponseEntity<>();
        response.setData(data);
        return response;
    }
    public static <T> CustomResponseEntity<T> error(String error) {
        CustomResponseEntity<T> response = new CustomResponseEntity<>();
        response.setError(error);
        return response;
    }
}
