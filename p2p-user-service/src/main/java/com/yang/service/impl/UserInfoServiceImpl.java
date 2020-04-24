package com.yang.service.impl;

import com.yang.entity.UserInfo;
import com.yang.mapper.UserInfoMapper;
import com.yang.service.UserServier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserServier {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
public UserInfo selectUserInfoById(Long id) {
    UserInfo userInfo = userInfoMapper.selectUserInfoById(id);
    return userInfo;
}
}
