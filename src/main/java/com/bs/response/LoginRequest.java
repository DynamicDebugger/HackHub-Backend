package com.bs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
