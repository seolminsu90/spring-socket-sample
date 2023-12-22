package com.notification.api.common.security;

import java.io.IOException;

import com.notification.api.common.util.ResponseWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class RestDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ade)
            throws IOException, ServletException {
        ResponseWriter.write(response, "유효하지만 권한이 없는 사용자", HttpServletResponse.SC_FORBIDDEN, null);
    }

}
