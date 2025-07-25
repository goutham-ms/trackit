package com.trackit.userservice.service;

import com.trackit.userservice.dto.UserInfoDto;
import com.trackit.userservice.entity.UserInfo;
import com.trackit.userservice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;


@Service
public class UserInfoService {

    private UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfoDto createOrUpdateUser(UserInfoDto userInfoDto) {
        Supplier<UserInfo> createUser = () -> {
            return userInfoRepository.save(convertToUserInfo(userInfoDto));
        };

        Function<UserInfo, UserInfo> updateUser = user -> {
            user.setFirstName(userInfoDto.getFirstName());
            user.setLastName(userInfoDto.getLastName());
            user.setEmail(userInfoDto.getEmail());
            user.setPhoneNumber(userInfoDto.getPhoneNumber());
            return userInfoRepository.save(user);
        };

        UserInfo userInfo = userInfoRepository.findByUserId(userInfoDto.getUserId())
                .map(updateUser)
                .orElseGet(createUser);

        return convertToDto(userInfo);
    }

    public UserInfoDto getUser(String userId) throws Exception{
        Optional<UserInfo> user = userInfoRepository.findByUserId(userId);
        if(user.isEmpty()) {
            throw new Exception("User not found!");
        }
        UserInfo userInfo = user.get();
        return convertToDto(userInfo);
    }


    public UserInfo convertToUserInfo(UserInfoDto userInfo) {
        return UserInfo.builder()
                .userId(userInfo.getUserId())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .phoneNumber(userInfo.getPhoneNumber())
                .email(userInfo.getEmail())
                .build();
    }

    public UserInfoDto convertToDto(UserInfo userInfo) {
        return UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .phoneNumber(userInfo.getPhoneNumber())
                .email(userInfo.getEmail())
                .build();
    }
}
