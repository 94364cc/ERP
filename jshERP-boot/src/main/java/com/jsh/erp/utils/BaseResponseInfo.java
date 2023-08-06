package com.jsh.erp.utils;

import com.jsh.erp.exception.ResultEnum;

public class BaseResponseInfo {
	public int code;
	public Object data;

	public BaseResponseInfo(int code) {
		this.code = code;
	}

	public BaseResponseInfo() {
		this.code = 400;
		this.data = null;
	}

	public static <T> BaseResponseInfo success() {
		return new BaseResponseInfo(ResultEnum.SUCCESS.getCode());
	}

}
