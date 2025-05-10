package com.beunreal.repository;

import com.beunreal.model.Friendship;
import com.beunreal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, String> {
    List<Friendship> findByRequester(User requester);
    List<Friendship> findByReceiver(User receiver);
}
