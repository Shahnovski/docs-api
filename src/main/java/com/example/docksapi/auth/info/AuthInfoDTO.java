package com.example.docksapi.auth.info;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthInfoDTO {
    private String status;
    private Integer code;
    private String details;
    private Long id;
    private String username;
    private String roles;

    public String getJson() {
        return new Gson().toJson(this);
    }
}
