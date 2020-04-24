package com.yang.vo;

public enum  CodeMsg {


    success(200,"成功"),

    InternalError(500,"代码错误"),

    loginfall(10001,"用户名或密码错误"),
    password_fall(10002,"密码错误"),
    accountexists(20001,"用户不存在"),
    user_sesson_expTRE(10003,"用户未登录或过期"),

    account_create_fall(20002,"创建失败"),

    account_not_exists(10005,"账户不存在"),

    Insert_BidRequest(10008,"借款申请失败"),

    BORROW_LOCK_ERROR(10009,"乐观锁失败"),

    BORROW_AUDIT_ERROT(100010,"审核失败");



    private int code;

    private String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public CodeMsg fillArgs(String arg){

        String newmsg = String.format(this.msg, arg);
        this.msg=newmsg;
        return this;

    }



}
