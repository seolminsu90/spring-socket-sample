package com.notification.api.core.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

    // 기본적인 일반 웹 AUTH 구현
    @GetMapping("/test")
    public String test() {
        return "test is free";
    }
    @GetMapping("/api/noauth")
    public String test2() {
        return "No auth";
    }

    @Secured("ROLE_TEST")
    @GetMapping("/api/auth")
    public String test3() {
        return "auth";
    }
}
