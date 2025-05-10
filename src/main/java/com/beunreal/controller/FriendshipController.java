package com.beunreal.controller;

import com.beunreal.model.Friendship;
import com.beunreal.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin(origins = "*")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping("/add")
    public ResponseEntity<Friendship> addFriend(
            @RequestParam String requesterId,
            @RequestParam String receiverEmail) {

        Optional<Friendship> friendship = friendshipService.sendFriendRequest(requesterId, receiverEmail);

        return friendship
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
