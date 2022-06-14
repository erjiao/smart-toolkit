package com.bmsoft.toolkit.exception;

import com.bmsoft.toolkit.BaseCode;
import com.bmsoft.toolkit.Code;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务类异常
 * @author llk
 * @date 2019-10-09 21:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private BaseCode code = Code.ERROR;

    private String message;

    public BusinessException(BaseCode code) {
        super(code.getMsg());
        this.code = code;
        this.message = code.getMsg();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(BaseCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(BaseCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
