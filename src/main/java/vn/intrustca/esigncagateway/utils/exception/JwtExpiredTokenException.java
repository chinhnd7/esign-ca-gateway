package vn.intrustca.esigncagateway.utils.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException {
    public JwtExpiredTokenException(String msg) {
        super(msg);
    }
}