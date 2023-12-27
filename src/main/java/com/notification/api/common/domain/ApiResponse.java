package com.notification.api.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.notification.api.common.exception.StatusCode;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    public T data;
    @JsonIgnore
    private StatusCode statusCode;
    private String message;

    @JsonProperty("code")
    private int getCode() {
        return (this.statusCode == null) ? StatusCode.SUCCESS.getCode() : this.statusCode.getCode();
    }

    public static <T> ApiResponse<T> of(StatusCode statusCode, T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(statusCode);
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse<T> ofData(StatusCode statusCode, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(statusCode);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> ofMessage(StatusCode statusCode, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse<T> ofCode(StatusCode statusCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(statusCode);
        return response;
    }
}
