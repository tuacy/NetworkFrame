package com.tuacy.networkframe.library.exception;

/**
 * 网络请求，发生异常
 */
public class ProtocolsException extends Exception {

	private static final long serialVersionUID = -954026383517356797L;

	private ProtocolsError mErrorCode;

	public ProtocolsException(ProtocolsError errorCode) {
		this.mErrorCode = errorCode;
	}

	public ProtocolsException(Throwable throwable, ProtocolsError errorCode) {
		super(throwable);
		mErrorCode = errorCode;
	}

	public void setErrorCode(ProtocolsError errorCode) {
		mErrorCode = errorCode;
	}

	public ProtocolsError getErrorCode() {
		return mErrorCode;
	}

	@Override
	public String toString() {
		return "ProtocolsException{" + "mErrorCode=" + mErrorCode + '}';
	}
}
