package com.sanghye.webservice.web;

import com.sanghye.webservice.UnAuthenticationException;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.dto.user.UserLoginRequestDto;
import com.sanghye.webservice.security.LoginUser;
import com.sanghye.webservice.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody User user) {
        User savedUser = userService.add(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/users/" + savedUser.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserLoginRequestDto loginDto) throws UnAuthenticationException {
        User loginUser = userService.login(loginDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/users/" + loginUser.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
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
