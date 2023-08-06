package com.jsh.erp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zoluo
 * @date 2021-04-27 10:27
 */
@Getter
@AllArgsConstructor
public enum ResultEnum implements ResultExceptionAssert {
    SUCCESS(200, "处理成功"),
    FAILURE(400, "处理失败"),

    // 系统级别异常码
    SERVER_ERROR(-1, "服务异常"),
    BUSINESS_ERROR(5000, "业务异常"),
    IN_SERVICE_ERROR(5003, "内部服务调用异常"),
    PARAM_ERROR(4000, "参数异常"),
    AUTH_ERROR(4001, "登录异常"),
    FORBIDDEN_ERROR(4003, "权限异常"),
    NOT_FOUND_ERROR(4004, "服务未找到"),

    MATERIAL_SAVE_ERROR(1001,"商品已经存在")




    ;



    final int code;
    final String message;
}
