package com.yang.entity;

import com.yang.util.BitStatesUtils;
import lombok.Data;

@Data
public class UserInfo {

    private Long id;

    private int version;

    private Long bitState;//位状态

    private String realName;//对应实名认证中的真实姓名

    private String idNumber;//对应实名认证中的身份证号

    private String email;//用户邮箱

    private String phoneNumber="";//手机号

    private int authScore=0;//用户当前认证的分数

    private Long realauthId;//用户有效的实名认证对象


  //基本资料
    private SystemDictionaryItem incomeGrade;//月收入

    private SystemDictionaryItem marriage;//婚姻情况

    private SystemDictionaryItem kidCount;//子女情况

    private SystemDictionaryItem educationBackground;//学历

    private SystemDictionaryItem houseCondition;//住房条件

    public void addState(Long state) {
        this.bitState = BitStatesUtils.addState(this.bitState, state);
    }

    public void  removeState(Long state) {
        this.bitState = BitStatesUtils.removeState(this.bitState, state);
    }


    public boolean getIsBindPhone() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_BIND_PHONE);
    }

    public boolean getIsBindEmail() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_BIND_EMAIL);
    }

    public boolean getBaseInfo() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_BASE_INFO);
    }

    public boolean getRealAuth() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_REAL_AUTH);
    }

    public boolean getVedioAuth() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_VEDIO_AUTH);
    }

    public boolean getHasBidRequest() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_HAS_BIDREQUEST);
    }

    public String getAnonymousRealName() {
        //TODO : 张三--->张*   李四光--->李**
        return this.realName;
    }


    public static UserInfo empty(Long id){
        UserInfo userInfo=new UserInfo();
        userInfo.setId(id);
        userInfo.setBitState(BitStatesUtils.OP_BASIC_INFO);
        return userInfo;
    }


















}
