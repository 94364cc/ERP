package com.jsh.erp.exception;

import com.jsh.erp.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author zoluo
 * @date 2021-04-27 14:41
 */
@Slf4j
@RestControllerAdvice
public class ResultExceptionHandler {

    @ExceptionHandler(ResultException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleResultException(ResultException e) {
        log.error("Result exception: [{}]", e.getMessage(), e);
        return R.failure();
    }


}
