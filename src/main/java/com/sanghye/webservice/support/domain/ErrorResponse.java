package com.sanghye.webservice.support.domain;

import lombok.Builder;
import lombok.Getter;

import static com.sanghye.webservice.support.domain.ResponseCode.ERROR;

@Getter
public class ErrorResponse {
    private String code;
    private String message;
    private Object information;

    public ErrorResponse() {
        this.code = ERROR.getCode();
        this.message = ERROR.getMessage();
    }

    @Builder
    public ErrorResponse(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Builder
    public ErrorResponse(String code, String message, Object information) {
        this.code = code;
        this.message = message;
        this.information = information;
    }

    @Builder
    public ErrorResponse(Object information) {
        this();
        this.information = information;
    }
}
