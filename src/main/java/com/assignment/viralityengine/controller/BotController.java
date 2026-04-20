package com.assignment.viralityengine.controller;

import com.assignment.viralityengine.entity.Bot;
import com.assignment.viralityengine.repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bots")
public class BotController {

    @Autowired
    private BotRepository botRepository;

    @PostMapping
    public ResponseEntity<Bot> createBot(@RequestBody Bot bot) {
        Bot savedBot = botRepository.save(bot);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBot);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bot> getBot(@PathVariable Long id) {
        return botRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
