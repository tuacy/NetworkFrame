package com.tuacy.networkframe.library.exception;

/**
 * 网络请求，发生异常
 */
public class ProtocolException extends Exception {

	private static final long serialVersionUID = -954026383517356797L;

	private final ProtocolError mErrorCode;

	public ProtocolException(ProtocolError errorCode) {
		this.mErrorCode = errorCode;
	}

	public ProtocolException(ProtocolError errorCode, String detailMessage) {
		super(detailMessage);
		mErrorCode = errorCode;
	}

	public ProtocolException(ProtocolError errorCode, String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		mErrorCode = errorCode;
	}

	public ProtocolError errorCode() {
		return mErrorCode;
	}

}
