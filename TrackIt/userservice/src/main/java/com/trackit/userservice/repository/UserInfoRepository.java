package com.trackit.userservice.repository;

import com.trackit.userservice.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, String> {
    Optional<UserInfo> findByUserId(String userId);
}

