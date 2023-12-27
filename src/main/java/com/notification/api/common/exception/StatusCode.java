package com.notification.api.common.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum StatusCode {
    SUCCESS(0),
    FAIL(-1),
    UNAUTHORIZED(401),
    FORBIDDEN(403);

    private final int code;

    public static StatusCode get(Integer code) {
        StatusCode statusCode = codes.get(code);
        return statusCode == null ? FAIL : statusCode;
    }

    StatusCode(int code) {
        this.code = code;
    }


    private static final Map<Integer, StatusCode> codes =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(StatusCode::getCode, Function.identity())));
}
