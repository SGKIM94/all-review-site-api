package com.sanghye.webservice.support.domain;

import lombok.Builder;
import lombok.Getter;

import static com.sanghye.webservice.support.domain.ResponseCode.SUCCESS;

@Getter
public class BaseResponse {
    private String code;
    private String message;
    private Object information;

    public BaseResponse() {
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
    }

    @Builder
    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Builder
    public BaseResponse(String code, String message, Object information) {
        this.code = code;
        this.message = message;
        this.information = information;
    }

    @Builder
    public BaseResponse(Object information) {
        this();
        this.information = information;
    }
}
