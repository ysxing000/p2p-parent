package com.yang.util;

import java.util.List;

public class BitStatesUtils {

    public final static Long OP_BASIC_INFO=1L;

    public final static Long OP_BIND_PHONE=2L << 0;

    public final static Long OP_BIND_EMAIL = 2L << 1;//用户绑定邮箱
    public final static Long OP_BASE_INFO = 2L << 2;//填写基本资料
    public final static Long OP_REAL_AUTH = 2L << 3;//用户实名认证
    public final static Long OP_VEDIO_AUTH = 2L << 4;//视频认证
    public final static Long OP_HAS_BIDREQUEST=2L<<5;//是否有借款标


    /**
     * @param states 所有状态值
     * @param value  需要判断状态值
     * @return 是否存在
     */
    public static boolean hasState(long states, long value) {
        return (states & value) != 0;
    }

    /**
     * @param states 已有状态值
     * @param value  需要添加状态值
     * @return 新的状态值
     */
    public static long addState(long states, long value) {
        if (hasState(states, value)) {
            return states;
        }
        return (states | value);
    }

    /**
     * @param states 已有状态值
     * @param value  需要删除状态值
     * @return 新的状态值
     */
    public static long removeState(long states, long value) {
        if (!hasState(states, value)) {
            return states;
        }
        return states ^ value;
    }




}
