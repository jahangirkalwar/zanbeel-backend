package com.example.DummyService.model.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddDummyRequestDto {
    private String name;
    private Integer age;
}
