package com.example.docksapi.auth.config.security;

import com.example.docksapi.auth.info.AuthInfo;
import com.example.docksapi.auth.info.AuthInfoDTO;
import com.example.docksapi.auth.info.AuthInfoMapper;
import com.example.docksapi.common.ApplicationProperties;
import com.example.docksapi.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@AllArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final AuthInfoMapper authInfoMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, active from users where username=?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from users u join user_roles ur on u.id = ur.user_id where u.username = ?")
                .rolePrefix("ROLE_");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(ApplicationProperties.API_URL + "/docs/**").hasRole(Role.USER.toString())
                .antMatchers(ApplicationProperties.API_URL + "/auth*").permitAll()
                .antMatchers(ApplicationProperties.API_URL + "/auth-info").permitAll()
                .antMatchers(ApplicationProperties.API_URL + "/registration").permitAll()
                .antMatchers(ApplicationProperties.API_URL + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .permitAll()
                .loginProcessingUrl(ApplicationProperties.API_URL + "/auth")
                .and()
                .logout()
                .logoutUrl(ApplicationProperties.API_URL + "/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    private AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                Authentication authentication) throws IOException {
                AuthInfoDTO authInfoDTO = authInfoMapper.toAuthInfoDTO(
                        AuthInfo.builder()
                                .status("ok")
                                .code(200)
                                .details("Success login")
                                .build()
                );
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().append(authInfoDTO.getJson());
                httpServletResponse.setStatus(200);
            }
        };
    }

    private AuthenticationFailureHandler failureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                AuthenticationException e) throws IOException {
                AuthInfoDTO authInfoDTO = authInfoMapper.toAuthInfoDTO(
                        AuthInfo.builder()
                                .status("error")
                                .code(401)
                                .details("Authentication failure")
                                .build()
                );

                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().append(authInfoDTO.getJson());
                httpServletResponse.setStatus(401);
            }
        };
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse,
                               AccessDeniedException e) throws IOException {
                AuthInfoDTO authInfoDTO = authInfoMapper.toAuthInfoDTO(
                        AuthInfo.builder()
                                .status("error")
                                .code(403)
                                .details("Access denied")
                                .build()
                );
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().append(authInfoDTO.getJson());
                httpServletResponse.setStatus(403);
            }
        };
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
                AuthInfoDTO authInfoDTO = authInfoMapper.toAuthInfoDTO(
                        AuthInfo.builder()
                                .status("ok")
                                .code(200)
                                .details("Success logout")
                                .build()
                );
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().append(authInfoDTO.getJson());
                httpServletResponse.setStatus(200);
            }
        };
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {

        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse,
                                 AuthenticationException e) throws IOException {
                AuthInfoDTO authInfoDTO = authInfoMapper.toAuthInfoDTO(
                        AuthInfo.builder()
                                .status("error")
                                .code(401)
                                .details("Not authenticated")
                                .build()
                );
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().append(authInfoDTO.getJson());
                httpServletResponse.setStatus(401);
            }
        };
    }
}
