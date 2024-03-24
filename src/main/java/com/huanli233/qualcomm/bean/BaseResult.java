package com.huanli233.qualcomm.bean;

public class BaseResult<T> {
	/**
	 * Result Code
	 */
	int code;
	/**
	 * Desc
	 */
	String desc;
	/**
	 * Data
	 */
	T data;
	/**
	 * @return code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code 要设置的 code
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc 要设置的 desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return data
	 */
	public T getData() {
		return data;
	}
	/**
	 * @param data 要设置的 data
	 */
	public void setData(T data) {
		this.data = data;
	}
	
	public BaseResult(int code, String desc, T data) {
		this.code = code;
		this.desc = desc;
		this.data = data;
	}
	
	public BaseResult(int code, T data) {
		this(code, null, data);
	}
	
	public BaseResult(T data) {
		this(0, data);
	}
	
}
