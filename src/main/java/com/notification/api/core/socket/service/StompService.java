package com.notification.api.core.socket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StompService {
    private final SimpUserRegistry simpUserRegistry;
}
