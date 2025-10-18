package com.renato.projects.redesocial.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendFriendRequest(String username, FriendRequestDTO request) {
        // envia para /user/{username}/topic/friend-requests
        messagingTemplate.convertAndSendToUser(
                username,
                "/topic/friend-requests",
                request
        );
    }
}
