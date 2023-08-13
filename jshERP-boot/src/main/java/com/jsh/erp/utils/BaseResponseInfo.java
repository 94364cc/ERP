package com.jsh.erp.utils;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsh.erp.exception.ResultEnum;
import com.jsh.erp.exception.ResultInterface;

public class BaseResponseInfo {
	public int code;
	@JsonIgnore
	public Map<String,String> messageMap = new HashMap<>();
	public Object data;

	public BaseResponseInfo(int code) {
		this.code = code;
	}

	public static BaseResponseInfo data(Object data) {
		return new BaseResponseInfo(ResultEnum.SUCCESS.getCode(), data);
	}

	public BaseResponseInfo(int code,Object data) {
		this.code = 200;
		this.data = data;
	}
	public BaseResponseInfo(String message) {
		this.code = 400;
		messageMap.put("message",message);
		this.data = messageMap;
	}

	public BaseResponseInfo(int code,String message) {
		messageMap.put("message",message);
		this.code = code;
		this.data = messageMap;
	}

	private BaseResponseInfo(ResultInterface result) {
		messageMap.put("message",result.getMessage());
		this.code = result.getCode();
		this.data = messageMap;
	}
	public BaseResponseInfo() {
		this.code = 400;
		this.data = null;
	}

	public static <T> BaseResponseInfo success() {
		return new BaseResponseInfo(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage());
	}

	public static <T> BaseResponseInfo failure() {
		return new BaseResponseInfo(ResultEnum.FAILURE.getCode(),ResultEnum.FAILURE.getMessage());
	}

	public static <T> BaseResponseInfo failure(String message) {
		return new BaseResponseInfo(ResultEnum.FAILURE.getCode(),message);
	}

}
