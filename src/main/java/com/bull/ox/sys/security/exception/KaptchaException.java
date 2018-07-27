package com.bull.ox.sys.security.exception;

import org.apache.shiro.authc.AuthenticationException;

public class KaptchaException extends AuthenticationException {

    public KaptchaException(){

    }

    public KaptchaException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public KaptchaException(String message) {
        super(message);
    }

    public KaptchaException(Throwable throwable) {
        super(throwable);
    }
}
