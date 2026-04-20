package com.assignment.viralityengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class NotificationScheduler {

    @Autowired
    private RedisService redisService;

    @Scheduled(fixedRate = 300000)
    public void sweepPendingNotifications() {
        Set<String> keys = redisService.getKeys("user:*:pending_notifs");
        
        if (keys == null || keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            List<String> notifications = redisService.getList(key);
            
            if (notifications != null && !notifications.isEmpty()) {
                String userId = key.split(":")[1];
                int count = notifications.size();
                
                String firstBot = notifications.get(0).split(" ")[1];
                
                if (count == 1) {
                    System.out.println("Summarized Push Notification to User " + userId + ": " + notifications.get(0));
                } else {
                    System.out.println("Summarized Push Notification to User " + userId + ": Bot " + firstBot + " and " + (count - 1) + " others interacted with your posts.");
                }
                
                redisService.deleteList(key);
            }
        }
    }

}
