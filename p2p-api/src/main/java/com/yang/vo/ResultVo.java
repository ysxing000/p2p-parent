package com.yang.vo;

import lombok.Data;

@Data
public class ResultVo<T> {

    private int code;//状态码

    private String msg;//消息

    private T data;//数据


    public ResultVo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //成功时
    public static  <T> ResultVo success(T data){
        return new ResultVo<>(200, "成功", data);
    }
    //失败时用的
    public static <T> ResultVo error(CodeMsg codeMsg){
        return new ResultVo(codeMsg.getCode(),codeMsg.getMsg(),null);

    }






}
