package com.notification.api.core.socket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.notification.api.common.exception.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocketPayload {
    @JsonIgnore
    private StatusCode statusCode;
    private String sender;
    private String message;

    @JsonProperty("code")
    private int getCode() {
        return (this.statusCode == null) ? StatusCode.SUCCESS.getCode() : this.statusCode.getCode();
    }
}
