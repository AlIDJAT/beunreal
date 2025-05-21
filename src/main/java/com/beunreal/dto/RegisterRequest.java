package com.beunreal.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    private int age;
    private String profileImageUrl;
    private Double latitude;
    private Double longitude;
}
