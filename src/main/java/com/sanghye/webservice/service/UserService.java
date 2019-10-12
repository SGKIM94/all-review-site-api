package com.sanghye.webservice.service;

import com.sanghye.webservice.UnAuthenticationException;
import com.sanghye.webservice.UnAuthorizedException;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.domain.UserRepository;
import com.sanghye.webservice.dto.user.UserLoginRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserService {
    @Resource(name = "userRepository")
    private UserRepository userRepository;

    public User add(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(User loginUser, long id, User updatedUser) {
        User original = findById(loginUser, id);
        original.update(loginUser, updatedUser);
        return original;
    }

    public User findById(User loginUser, long id) {
        return userRepository.findById(id)
                .filter(user -> user.equals(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User checkLoginUser(String userId, String password) throws UnAuthenticationException {
        return userRepository.findByUserId(userId)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(UnAuthenticationException::new);
    }

    User checkLoginUserToDto(UserLoginRequestDto loginDto) throws UnAuthenticationException {
        return userRepository.findByUserId(loginDto.getUserId())
                .filter(user -> user.matchPassword(loginDto.getPassword()))
                .orElseThrow(UnAuthenticationException::new);
    }

    public void login(UserLoginRequestDto loginDto) throws UnAuthenticationException {
        checkLoginUserToDto(loginDto);
    }
}
