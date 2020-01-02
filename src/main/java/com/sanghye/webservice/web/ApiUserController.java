package com.sanghye.webservice.web;

import com.sanghye.webservice.dto.user.UserRegisterRequestDto;
import com.sanghye.webservice.dto.user.UserRegisterResponseDto;
import com.sanghye.webservice.exception.UnAuthenticationException;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.dto.user.UserLoginRequestDto;
import com.sanghye.webservice.security.LoginUser;
import com.sanghye.webservice.security.TokenAuthenticationService;
import com.sanghye.webservice.service.UserService;
import com.sanghye.webservice.support.domain.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "tokenService")
    private TokenAuthenticationService tokenAuthenticationService;

    @PostMapping("")
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody UserRegisterRequestDto user) {
        User savedUser = userService.addByRegisterRequestDto(user);
        return new ResponseEntity<>(new BaseResponse(UserRegisterResponseDto.toDtoEntity(savedUser)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody UserLoginRequestDto loginDto) throws UnAuthenticationException {
        User loginUser = userService.login(loginDto);
        String token = tokenAuthenticationService.toJwtByUserId(loginUser.getUserId());
        return new ResponseEntity<>(new BaseResponse(token), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public User show(@LoginUser User loginUser, @PathVariable long id) {
        return userService.findById(loginUser, id);
    }

    @PutMapping("{id}")
    public User update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody User updatedUser) {
        return userService.update(loginUser, id, updatedUser);
    }
}
