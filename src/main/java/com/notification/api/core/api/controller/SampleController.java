package com.notification.api.core.api.controller;

import com.notification.api.common.exception.StatusCode;
import com.notification.api.core.api.entity.Sample;
import com.notification.api.core.api.repository.SampleRepository;
import com.notification.api.core.socket.domain.SocketPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final SampleRepository sampleRepository;
    private final SimpMessagingTemplate messagingTemplate;

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

    @GetMapping("/test/samples")
    public List<Sample> queryDSLTest() {
        return sampleRepository.findAllUser();
    }

    @GetMapping("/test/notificate-all")
    public String test(@RequestParam(name = "msg") String msg) {
        SocketPayload payload = new SocketPayload(StatusCode.SUCCESS, "운영자", msg);
        messagingTemplate.convertAndSend("/topic/notification-topic", payload);
        return "test is free";
    }
}
