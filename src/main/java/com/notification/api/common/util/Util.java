package com.notification.api.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {
    private final ObjectMapper om = new ObjectMapper();

    public static ObjectMapper mapper() {
        return om;
    }

    public static String writeValueAsString(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
