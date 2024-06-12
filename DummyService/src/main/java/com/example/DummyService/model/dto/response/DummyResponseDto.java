package com.example.DummyService.model.dto.response;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DummyResponseDto {
    private Long id;
    private String name;
    private int age;
}
