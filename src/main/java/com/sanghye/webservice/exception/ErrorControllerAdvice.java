package com.sanghye.webservice.exception;


import com.sanghye.webservice.support.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.sanghye.webservice.support.domain.ResponseCode.NO_AUTHENTICATION;

@Slf4j
@ControllerAdvice
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ErrorControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception() {
        log.error("서버에 오류가 발생하였습니다.");
        return new ResponseEntity<>(new ErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
