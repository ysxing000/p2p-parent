package com.yang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {

    //用户的状态 0为正常 1为锁定 -1为删除
    private static final int STATE_NORMAL=0;
    private static final int STATE_LOCK=1;
    private static final int STATE_DELETE=-1;

    private static final int usertype_normal=0;

    private static final int usertype_system=1;

    private Long id;

    private String username;

    private String password;

    Integer state=STATE_NORMAL;

    Integer usertype=usertype_normal;//用户类型
    boolean admin=false;
    public static LoginInfo empty(Long id){
        LoginInfo  loginInfo = new LoginInfo();
        loginInfo.setId(id);
        return  loginInfo;
    }









}
