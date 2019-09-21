package com.videoadmin.exception;

import com.videoadmin.utils.HttpCode;


@SuppressWarnings("serial")
public class FtpException extends BaseException {
	public FtpException() {
	}

	public FtpException(String message) {
		super(message);
	}

	public FtpException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	protected HttpCode getHttpCode() {
        return HttpCode.INTERNAL_SERVER_ERROR;
    }
}
