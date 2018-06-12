package com.viewhigh.analysis.exception;

import com.viewhigh.analysis.domain.ErrorInfo;

public class CheckedException extends RuntimeException {

	private static final long serialVersionUID = -4162847215566209030L;
	
	private String msg;
	private String code;

	public CheckedException(String msg) {
		super();
		this.msg = msg;
	}

	public CheckedException(String code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public CheckedException(ErrorInfo errorInfo) {
		this.msg = errorInfo.getMessage();
		this.code = errorInfo.getCode();
	}

	public void setErrorMessage(String errorMessage) {
		this.msg = errorMessage;
	}

	public String getErrorMessage() {
		return msg;
	}

	public void setErrorCode(String errorCode) {
		this.code = errorCode;
	}

	public String getErrorCode() {
		return code;
	}
}
