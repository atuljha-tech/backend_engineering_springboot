package com.assignment.viralityengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViralityService {

    @Autowired
    private RedisService redisService;

    private static final int BOT_REPLY_POINTS = 1;
    private static final int HUMAN_LIKE_POINTS = 20;
    private static final int HUMAN_COMMENT_POINTS = 50;

    public void incrementViralityForBotReply(Long postId) {
        String key = "post:" + postId + ":virality_score";
        redisService.incrementBy(key, BOT_REPLY_POINTS);
    }

    public void incrementViralityForHumanLike(Long postId) {
        String key = "post:" + postId + ":virality_score";
        redisService.incrementBy(key, HUMAN_LIKE_POINTS);
    }

    public void incrementViralityForHumanComment(Long postId) {
        String key = "post:" + postId + ":virality_score";
        redisService.incrementBy(key, HUMAN_COMMENT_POINTS);
    }

    public Long getViralityScore(Long postId) {
        String key = "post:" + postId + ":virality_score";
        return redisService.getCounter(key);
    }

}
