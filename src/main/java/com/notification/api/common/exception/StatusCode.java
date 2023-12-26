package com.notification.api.common.exception;

import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS(0),
    FAIL(-1);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

}
