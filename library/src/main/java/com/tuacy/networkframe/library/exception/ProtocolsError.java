package com.tuacy.networkframe.library.exception;

/**
 * 网络异常类型
 */
public enum ProtocolsError {

	/**
	 * http 错误, 404之类的。
	 */
	ERROR_HTTP,
	/**
	 * time out
	 */
	ERROR_TIMEOUT,
	/**
	 * 主机名解析不出来
	 */
	ERROR_HOST,
	/**
	 * 数据格式错误
	 */
	ERROR_FORMAT,
	/**
	 * 网络权限问题
	 */
	ERROR_PERMISSION,
	/**
	 * 未知错误
	 */
	ERROR_UNKNOWN

}
