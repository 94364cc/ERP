package com.jsh.erp.utils;

import com.jsh.erp.exception.ResultEnum;

public class R {
	public int code;
	public Object data;

	private R(int code) {
		this.code = code;
	}

	public static <T> R success() {
		return new R(ResultEnum.SUCCESS.getCode());
	}
	public static <T> R failure() {
		return new R(ResultEnum.FAILURE.getCode());
	}

}
