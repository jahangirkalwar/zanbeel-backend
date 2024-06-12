package com.example.DummyService.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum DummyMessage {
    USER_DELETED(1, "User deleted successfully"),
    USER_NOT_FOUND(2, "User not Found"),
    INPUT_FIELD_NOT_PRESENT(3, "Input fields are not present");

    private int code;
    private String value;
}
