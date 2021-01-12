package com.example.docksapi.auth.info;

import com.example.docksapi.common.ApplicationProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApplicationProperties.API_URL)
@AllArgsConstructor
public class AuthInfoController {

    private final AuthInfoService authInfoService;

    @GetMapping(value = "/auth-info", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public AuthInfoDTO getAuthInfo(Authentication authentication) {
        return authInfoService.getAuthInfo(authentication);
    }
}
