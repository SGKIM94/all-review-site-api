package com.sanghye.webservice.service;

import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.domain.UserRepository;
import com.sanghye.webservice.dto.user.LoginRequestDto;
import com.sanghye.webservice.dto.user.LoginResponseDto;
import com.sanghye.webservice.dto.user.RegisterRequestDto;
import com.sanghye.webservice.dto.user.RegisterResponseDto;
import com.sanghye.webservice.exception.DuplicateUserException;
import com.sanghye.webservice.exception.UnAuthenticationException;
import com.sanghye.webservice.exception.UnAuthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserService {
    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Transactional
    public User add(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public RegisterResponseDto addByRegisterRequestDto(RegisterRequestDto user) {
        User saveUser = userRepository.save(user.toEntity(user));
        return RegisterResponseDto.toDtoEntity(saveUser);
    }

    public void checkUserExist(String userId) {
        if (isExistUser(userId)) {
            throw new DuplicateUserException("이미 존재하는 유저입니다.");
        }
    }

    @Transactional(readOnly = true)
    public boolean isExistUser(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    @Transactional(readOnly = true)
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public User update(User loginUser, long id, User updatedUser) {
        User original = findById(loginUser, id);
        original.update(loginUser, updatedUser);
        return original;
    }

    @Transactional(readOnly = true)
    public User findById(User loginUser, long id) {
        return userRepository.findById(id)
                .filter(user -> user.equals(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User checkLoginUser(String userId, String password) throws UnAuthenticationException {
        return userRepository.findByUserId(userId)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(UnAuthenticationException::new);
    }

    @Transactional(readOnly = true)
    User checkLoginUserToDto(LoginRequestDto loginDto) throws UnAuthenticationException {
        return userRepository.findByUserId(loginDto.getUserId())
            .filter(user -> user.matchPassword(loginDto.getPassword()))
            .orElseThrow(UnAuthenticationException::new);
    }

    @Transactional(readOnly = true)
    public User login(LoginRequestDto loginDto) throws UnAuthenticationException {
        return checkLoginUserToDto(loginDto);
    }

    public LoginResponseDto makeLoginResponseDto(User loginUser, String token) {
        return LoginResponseDto.toDtoEntity(loginUser, token);
    }
}
