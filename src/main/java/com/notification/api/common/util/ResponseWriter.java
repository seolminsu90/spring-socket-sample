package com.notification.api.common.util;

import com.notification.api.common.domain.ApiResponse;
import com.notification.api.common.exception.StatusCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWriter {

    public static void write(HttpServletResponse response, String msg, int code) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code);

        ApiResponse<String> apiResponse = ApiResponse.ofMessage(StatusCode.get(code), msg);
        String jsonString = Util.writeValueAsString(apiResponse);

        PrintWriter out = response.getWriter();
        out.print(jsonString);
        out.flush();
        out.close();
    }
}