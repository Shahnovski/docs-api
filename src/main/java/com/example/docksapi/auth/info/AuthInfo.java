package com.example.docksapi.auth.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthInfo {
    private String status;
    private Integer code;
    private String details;
    private Long id;
    private String username;
    private String roles;
}

