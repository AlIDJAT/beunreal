package com.beunreal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "friendships")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester; // celui qui envoie l'invitation

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver; // celui qui la reçoit

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    public enum FriendshipStatus {
        PENDING,
        ACCEPTED,
        BLOCKED
    }
}
