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
@ControllerAdvice(annotations = Controller.class)
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class BusinessControllerAdvice {

    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<ErrorResponse> UnAuthenticationExceptionHandler() {
        log.error("존재하지 않는 유저입니다.");
        return new ResponseEntity<>(new ErrorResponse(NO_AUTHENTICATION), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
