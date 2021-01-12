package com.example.docksapi.auth.registration;

import com.example.docksapi.auth.info.AuthInfoDTO;
import com.example.docksapi.common.ApplicationProperties;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApplicationProperties.API_URL)
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(value = "/registration", produces = "application/json; charset=UTF-8")
    public AuthInfoDTO addUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        return registrationService.addUser(username, password);
    }

}
