package com.yang.exception;

import com.yang.vo.CodeMsg;
import lombok.Data;

@Data
public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
