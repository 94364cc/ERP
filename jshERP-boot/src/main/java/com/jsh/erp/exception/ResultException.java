package com.jsh.erp.exception;

/**
 * @author zoluo
 * @date 2021-04-27 14:39
 */
public class ResultException extends RuntimeException {

    private static final long serialVersionUID = 3235272943981785037L;

    public ResultInterface resultInterface;
    protected Object[] args;


    public ResultException(ResultInterface resultInterface, Object[] args, String message) {
        super(message);
        this.resultInterface = resultInterface;
        this.args = args;
    }

    public ResultException(ResultInterface resultInterface, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.resultInterface = resultInterface;
        this.args = args;
    }


    public ResultException(String msg) {
        super(msg);
    }

}
