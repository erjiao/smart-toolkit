package com.bmsoft.toolkit.exception;

import com.bmsoft.toolkit.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author llk
 * @date 2019-11-28 17:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FeignException extends RuntimeException {

    private Result result;

    private String methodKey;

    public FeignException(Result result) {
        super(result.getMsg());
        this.result = result;
    }

    public FeignException(Result result, String methodKey) {
        super(result.getMsg());
        this.result = result;
        this.methodKey = methodKey;
    }
}
