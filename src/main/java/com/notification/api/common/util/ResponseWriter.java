package com.notification.api.common.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWriter {
    public static void write(HttpServletResponse response, String msg, int code) throws IOException {
        write(response, msg, code, null);
    }

    public static void write(HttpServletResponse response, String msg, int code, String token) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code);

        StringBuilder json = new StringBuilder();
        json.append("{\"code\" : ").append(code).append(" , \"msg\" : \"").append(msg).append("\"");
        if (token != null)
            json.append(",\"token\" : \"").append(token).append(" \"");

        json.append("}");

        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
        out.close();
    }
}