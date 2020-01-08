package com.sanghye.webservice.exception;


import com.sanghye.webservice.support.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.sanghye.webservice.support.domain.ResponseCode.DUPLICATE_USER;
import static com.sanghye.webservice.support.domain.ResponseCode.NO_AUTHENTICATION;

@Slf4j
@ControllerAdvice
public class BusinessControllerAdvice {
    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<ErrorResponse> UnAuthenticationExceptionHandler() {
        log.error("존재하지 않는 유저입니다.");
        return new ResponseEntity<>(new ErrorResponse(NO_AUTHENTICATION), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> DuplicateUserExceptionHandler() {
        log.error("이미 가입된 회원입니다.");
        return new ResponseEntity<>(new ErrorResponse(DUPLICATE_USER), HttpStatus.OK);
    }
}
