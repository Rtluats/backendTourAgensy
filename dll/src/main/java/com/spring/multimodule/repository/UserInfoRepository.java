package com.spring.multimodule.repository;

import com.spring.multimodule.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	UserInfo findUserInfoByUserUsername(String username);
}
