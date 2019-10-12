package com.sanghye.webservice.service;

import com.sanghye.webservice.UnAuthenticationException;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.domain.UserRepository;
import com.sanghye.webservice.dto.user.UserLoginRequestDto;
import com.sanghye.webservice.support.test.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static com.sanghye.webservice.fixtures.User.SANGGU;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest extends BaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void login_success() throws Exception {
        User user = new User("sanjigi", "password", "name", "javajigi@slipp.net");
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));

        User loginUser = userService.checkLoginUser(user.getUserId(), user.getPassword());
        softly.assertThat(loginUser).isEqualTo(user);
    }

    @Test(expected = UnAuthenticationException.class)
    public void login_failed_when_user_not_found() throws Exception {
        when(userRepository.findByUserId("sanjigi")).thenReturn(Optional.empty());

        userService.checkLoginUser("sanjigi", "password");
    }

    @Test(expected = UnAuthenticationException.class)
    public void login_failed_when_mismatch_password() throws Exception {
        User user = new User("sanjigi", "password", "name", "javajigi@slipp.net");
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));

        userService.checkLoginUser(user.getUserId(), user.getPassword() + "2");
    }

    @Test
    public void UserLoginRequestDto_를_인자로받아서_login_이_되는가() throws UnAuthenticationException {
        //given
        String userId = "sangu";
        UserLoginRequestDto user = new UserLoginRequestDto().toDtoEntity(userId, "1234");

        //when
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(SANGGU));

        //then
        userService.checkLoginUserToDto(user);
    }
}
