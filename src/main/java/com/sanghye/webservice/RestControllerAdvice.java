package com.sanghye.webservice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class RestControllerAdvice {
    @ExceptionHandler(Exception.class)
    public String exception() {
        log.error("서버에 오류가 발생하였습니다.");
        return "/error/exception";
    }
}
