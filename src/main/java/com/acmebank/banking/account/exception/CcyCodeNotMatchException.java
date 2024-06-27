package com.acmebank.banking.account.exception;
// Check Sender and Receiver Currency Code not match
public class CcyCodeNotMatchException extends BusinessException {
    public CcyCodeNotMatchException(String message, String errorCode) {
        super(message, errorCode);
    }

}
