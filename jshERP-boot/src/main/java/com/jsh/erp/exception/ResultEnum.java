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

    MATERIAL_SAVE_ERROR(1001,"商品已经存在"),
    MATERIAL_STANDARD_ERROR(1002,"箱规填写不合规"),
    DOCUMENT_SEQENCE_ERROR(2001,"获取单据最大序列号失败"),
    DOCUMENT_SUPPLIER_ERROR(2002,"请填写客户编号"),
    DOCUMENT_DOCUMENT_ERROR(2003,"请填写仓库编号"),
    DOCUMENT_ITEM_NOT_EXISTS(2004,"单据详情不存在"),
    DOCUMENT_HEAD_NOT_EXISTS(2005,"单据主体不存在"),
    MATERIAL_STOCK_NOT_EXISTS(3001,"仓库下货物不存在"),
    OUT_DAYU_IN(3002,"出货数量超出商品库存"),



    ;



    final int code;
    final String message;
}
