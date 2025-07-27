package com.trackit.authservice.controller;

import com.trackit.authservice.entity.RefreshToken;
import com.trackit.authservice.request.AuthRequestDto;
import com.trackit.authservice.request.RefreshTokenRequestDto;
import com.trackit.authservice.response.JwtResponseDto;
import com.trackit.authservice.service.JwtService;
import com.trackit.authservice.service.RefreshTokenService;
import com.trackit.authservice.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class TokenController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("auth/v1/login")
    public ResponseEntity login(@RequestBody AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDto.getUsername());
            String userId = userDetailsService.getUserByUsername(authRequestDto.getUsername());

            if(Objects.nonNull(userId) && Objects.nonNull(refreshToken)) {
                return new ResponseEntity<>(JwtResponseDto.builder()
                        .accessToken(jwtService.generateToken(authRequestDto.getUsername()))
                        .token(refreshToken.getToken())
                        .userId(userId)
                        .build(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("auth/v1/refreshToken")
    public JwtResponseDto responseDto(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return refreshTokenService.findByToken(refreshTokenRequestDto.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUsername());
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDto.getToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh token is not available!"));
    }

}
