package com.tuacy.networkframe.library.exception;

/**
 * 网络请求，发生异常
 */
public class ProtocolException extends Exception {

	private static final long serialVersionUID = -954026383517356797L;

	private ProtocolError mErrorCode;

	public ProtocolException(ProtocolError errorCode) {
		this.mErrorCode = errorCode;
	}

	public ProtocolException(Throwable throwable, ProtocolError errorCode) {
		super(throwable);
		mErrorCode = errorCode;
	}

	public void setErrorCode(ProtocolError errorCode) {
		mErrorCode = errorCode;
	}

	public ProtocolError getErrorCode() {
		return mErrorCode;
	}

}
