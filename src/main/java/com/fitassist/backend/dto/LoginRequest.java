package com.fitassist.backend.dto; // backend eklendi

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}