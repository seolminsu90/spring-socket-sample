package com.notification.api.core.socket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class StompService {
    private final SimpUserRegistry simpUserRegistry;

    public Set<SimpUser> getSimpUsers() {
        return simpUserRegistry.getUsers();
    }
}
