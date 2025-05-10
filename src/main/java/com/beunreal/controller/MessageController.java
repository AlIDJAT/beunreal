package com.beunreal.controller;

import com.beunreal.model.Message;
import com.beunreal.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Map<String, String> payload) {
        String senderId = payload.get("senderId");
        String receiverId = payload.get("receiverId");
        String text = payload.get("text");
        String imageUrl = payload.get("imageUrl");

        Optional<Message> message = messageService.sendMessage(senderId, receiverId, text, imageUrl);

        return message
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/send-to-many")
    public ResponseEntity<List<Message>> sendToMany(@RequestBody Map<String, Object> payload) {
        String senderId = (String) payload.get("senderId");
        List<String> receiverIds = (List<String>) payload.get("receiverIds");
        String text = (String) payload.get("text");
        String imageUrl = (String) payload.get("imageUrl");

        List<Message> sent = messageService.sendMessageToMany(senderId, receiverIds, text, imageUrl);
        return ResponseEntity.ok(sent);
    }

    // ✅ Nouveau endpoint pour messages géolocalisés
    @GetMapping("/nearby")
    public ResponseEntity<List<Message>> getNearbyMessages(
            @RequestParam String userId,
            @RequestParam(defaultValue = "10") double radiusKm) {

        List<Message> messages = messageService.getNearbyMessages(userId, radiusKm);
        return ResponseEntity.ok(messages);
    }
}
