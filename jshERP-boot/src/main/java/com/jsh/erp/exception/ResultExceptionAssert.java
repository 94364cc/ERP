package com.jsh.erp.exception;

import java.text.MessageFormat;

/**
 * @author zoluo
 * @date 2021-04-27 14:49
 */
public interface ResultExceptionAssert extends ResultInterface, ResultAssert {
    @Override
    default ResultException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ResultException(this, args, msg);
    }

    @Override
    default ResultException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ResultException(this, args, msg, t);
    }
}
