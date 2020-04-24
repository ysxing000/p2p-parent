package com.yang.mapper;

import com.yang.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserInfoMapper {


    public int insertUserinfo(UserInfo userInfo);

    public UserInfo selectUserInfoById(Long id);


}
