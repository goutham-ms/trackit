package com.trackit.authservice.service;

import com.trackit.authservice.entity.UserInfo;
import com.trackit.authservice.model.UserInfoDto;
import com.trackit.authservice.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;


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


}

