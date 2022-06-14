package com.bmsoft.toolkit.exception;

import com.bmsoft.toolkit.Code;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author llk
 * @date 2020-08-17 23:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidIpException extends RuntimeException {

    private String message;

    public InvalidIpException() {
        this(Code.NOT_ALLOW.getMsg());
    }

    public InvalidIpException(String message) {
        super(message);
        this.message = message;
    }
}
