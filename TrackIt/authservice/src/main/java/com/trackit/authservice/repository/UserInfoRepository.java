package com.trackit.authservice.repository;

import com.trackit.authservice.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, String> {
    UserInfo findByUsername(String username);
}
