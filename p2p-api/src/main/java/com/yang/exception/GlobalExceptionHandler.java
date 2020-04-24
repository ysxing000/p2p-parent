package com.yang.exception;

import com.yang.vo.CodeMsg;
import com.yang.vo.ResultVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//捕获全局异常
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler
    public ResultVo handleException(Exception e){

        if(e instanceof GlobalException){

            GlobalException ge= (GlobalException) e;
            return  ResultVo.error(ge.getCodeMsg());
        }
        e.printStackTrace();
        return ResultVo.error(CodeMsg.InternalError);
    }





}
