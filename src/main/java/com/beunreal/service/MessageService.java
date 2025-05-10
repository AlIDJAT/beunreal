package com.beunreal.service;

import com.beunreal.model.Message;
import com.beunreal.model.User;
import com.beunreal.repository.MessageRepository;
import com.beunreal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Optional<Message> sendMessage(String senderId, String receiverId, String text, String imageUrl) {
        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findById(receiverId);

        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            Message message = Message.builder()
                    .sender(senderOpt.get())
                    .receiver(receiverOpt.get())
                    .text(text)
                    .imageUrl(imageUrl)
                    .sentAt(LocalDateTime.now())
                    .build();

            return Optional.of(messageRepository.save(message));
        }

        return Optional.empty();
    }

    public List<Message> sendMessageToMany(String senderId, List<String> receiverIds, String text, String imageUrl) {
        Optional<User> senderOpt = userRepository.findById(senderId);
        List<Message> sentMessages = new ArrayList<>();

        if (senderOpt.isPresent()) {
            for (String receiverId : receiverIds) {
                userRepository.findById(receiverId).ifPresent(receiver -> {
                    Message message = Message.builder()
                            .sender(senderOpt.get())
                            .receiver(receiver)
                            .text(text)
                            .imageUrl(imageUrl)
                            .sentAt(LocalDateTime.now())
                            .build();

                    sentMessages.add(messageRepository.save(message));
                });
            }
        }

        return sentMessages;
    }

    // ✅ méthode de calcul de distance
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Rayon de la Terre en km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // ✅ méthode pour récupérer les messages proches
    public List<Message> getNearbyMessages(String userId, double radiusKm) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return List.of();

        User user = userOpt.get();
        List<User> allUsers = userRepository.findAll();

        List<String> nearbyUserIds = allUsers.stream()
                .filter(u -> !u.getId().equals(userId))
                .filter(u -> u.getLatitude() != null && u.getLongitude() != null)
                .filter(u -> distance(user.getLatitude(), user.getLongitude(), u.getLatitude(), u.getLongitude()) <= radiusKm)
                .map(User::getId)
                .toList();

        return messageRepository.findAll().stream()
                .filter(m -> m.getSentAt().isAfter(LocalDateTime.now().minusHours(24)))
                .filter(m -> nearbyUserIds.contains(m.getSender().getId()))
                .toList();
    }
}
