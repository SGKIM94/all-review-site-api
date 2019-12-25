package com.sanghye.webservice.support.domain;

public enum  ResponseCode {
    SUCCESS("200", "success"),
    ERROR("999", "fail"),
    NO_AUTHENTICATION("998", "invalid user");

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
