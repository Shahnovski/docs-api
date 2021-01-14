package com.example.docksapi.auth.info;

import com.example.docksapi.exception.exceptions.NotAuthorizedException;
import com.example.docksapi.user.User;
import com.example.docksapi.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthInfoService {

    private final UserRepository userRepository;

    public User getUserByAuthentication(Authentication authentication) {
        if (authentication == null) throw new NotAuthorizedException();
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }

}
