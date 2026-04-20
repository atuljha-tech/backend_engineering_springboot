package com.assignment.viralityengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class NotificationService {

    @Autowired
    private RedisService redisService;

    private static final int NOTIFICATION_COOLDOWN_MINUTES = 15;

    public void handleBotInteraction(Long userId, String botName, String interactionType) {
        String cooldownKey = "user:" + userId + ":notif_cooldown";
        String pendingKey = "user:" + userId + ":pending_notifs";

        if (Boolean.TRUE.equals(redisService.exists(cooldownKey))) {
            String notification = "Bot " + botName + " " + interactionType;
            redisService.pushToList(pendingKey, notification);
        } else {
            System.out.println("Push Notification Sent to User " + userId + ": Bot " + botName + " " + interactionType);
            redisService.setWithExpiry(cooldownKey, "1", NOTIFICATION_COOLDOWN_MINUTES, TimeUnit.MINUTES);
        }
    }

}
