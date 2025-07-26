package com.trackit.authservice.service;

import com.trackit.authservice.entity.UserInfo;
import com.trackit.authservice.event.UserInfoProducer;
import com.trackit.authservice.model.UserInfoDto;
import com.trackit.authservice.model.UserInfoEventDto;
import com.trackit.authservice.repository.UserInfoRepository;
import com.trackit.authservice.utils.Validation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserInfoProducer userInfoProducer;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.trackit.authservice.entity.UserInfo user = userInfoRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Could not find the user..!");
        }
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoDto userInfoDto) {
        return userInfoRepository.findByUsername(userInfoDto.getUsername());
    }

    public String getUserByUsername(String username) {
        return Optional.of(userInfoRepository.findByUsername(username)).map(UserInfo::getUserId).orElse(null);
    }

    public String signupUser(UserInfoDto userInfoDto) {
        if(!Validation.validateUser(userInfoDto)) {
            return null;
        }
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDto))) {
            return null;
        }
        String userId = UUID.randomUUID().toString();
        UserInfo userInfo = new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>());
        userInfoRepository.save(userInfo);
        userInfoProducer.sendEventToKafka(converToUserInfoEvent(userInfoDto, userId));
        return userId;
    }

    private UserInfoEventDto converToUserInfoEvent(UserInfoDto userInfoDto, String userId) {
        return UserInfoEventDto.builder()
                .userId(userId)
                .firstName(userInfoDto.getFirstName())
                .lastName(userInfoDto.getLastName())
                .email(userInfoDto.getEmail())
                .phoneNumber(userInfoDto.getPhoneNumber())
                .build();
    }

}

