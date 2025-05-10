package com.beunreal.service;

import com.beunreal.model.Friendship;
import com.beunreal.model.User;
import com.beunreal.repository.FriendshipRepository;
import com.beunreal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public Optional<Friendship> sendFriendRequest(String requesterId, String receiverEmail) {
        Optional<User> requesterOpt = userRepository.findById(requesterId);
        Optional<User> receiverOpt = userRepository.findByEmail(receiverEmail);

        if (requesterOpt.isPresent() && receiverOpt.isPresent()) {
            Friendship friendship = Friendship.builder()
                    .requester(requesterOpt.get())
                    .receiver(receiverOpt.get())
                    .status(Friendship.FriendshipStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();

            return Optional.of(friendshipRepository.save(friendship));
        }

        return Optional.empty();
    }
}
