package com.trackit.authservice.controller;

import com.trackit.authservice.entity.RefreshToken;
import com.trackit.authservice.model.UserInfoDto;
import com.trackit.authservice.response.JwtResponseDto;
import com.trackit.authservice.service.JwtService;
import com.trackit.authservice.service.RefreshTokenService;
import com.trackit.authservice.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity signup(@RequestBody UserInfoDto userInfoDto) {
        try {
            String userId = userDetailsService.signupUser(userInfoDto);
            if(Objects.isNull(userId)) {
                return new ResponseEntity<>("User Already Exists!", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken = jwtService.generateToken(userInfoDto.getUsername());
            return new ResponseEntity<>(JwtResponseDto.builder().accessToken(jwtToken)
                    .token(refreshToken.getToken()).userId(userId).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception in UserService", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
