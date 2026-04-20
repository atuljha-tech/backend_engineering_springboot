package com.assignment.viralityengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class GuardrailService {

    @Autowired
    private RedisService redisService;

    private static final int MAX_BOT_REPLIES_PER_POST = 100;
    private static final int MAX_COMMENT_DEPTH = 20;
    private static final int COOLDOWN_MINUTES = 10;

    public boolean canBotReplyToPost(Long postId) {
        String key = "post:" + postId + ":bot_count";
        Long currentCount = redisService.getCounter(key);
        return currentCount < MAX_BOT_REPLIES_PER_POST;
    }

    public void incrementBotReplyCount(Long postId) {
        String key = "post:" + postId + ":bot_count";
        redisService.increment(key);
    }

    public boolean isDepthAllowed(int depth) {
        return depth <= MAX_COMMENT_DEPTH;
    }

    public boolean isCooldownActive(Long botId, Long humanId) {
        String key = "cooldown:bot_" + botId + ":human_" + humanId;
        return Boolean.TRUE.equals(redisService.exists(key));
    }

    public void setCooldown(Long botId, Long humanId) {
        String key = "cooldown:bot_" + botId + ":human_" + humanId;
        redisService.setWithExpiry(key, "1", COOLDOWN_MINUTES, TimeUnit.MINUTES);
    }

}
