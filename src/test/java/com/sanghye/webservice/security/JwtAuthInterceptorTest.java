package com.sanghye.webservice.security;

import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.service.UserService;
import com.sanghye.webservice.support.test.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class JwtAuthInterceptorTest extends BaseTest {
    public static final String KIM_ID = "tkdrn8578";
    public static final String KIM_PASSWORD = "password";
    @Mock
    private UserService userService;

    @InjectMocks
    private JwtAuthInterceptor basicAuthInterceptor;

    @InjectMocks
    private TokenAuthenticationService tokenAuthenticationService;

    @InjectMocks
    @Spy
    private TokenAuthenticationService tokenService;

    private static final String TOKEN_PREFIX = "Bearer";

    @Test
    public void JWT_preHandle_로그인_성공() throws Exception {
        String userId = KIM_ID;
        String password = KIM_PASSWORD;

        MockHttpServletRequest request = jwtAuthHttpRequest(userId);
        User loginUser = new User(userId, password, "name", "javajigi@slipp.net");
        when(userService.checkLoginUser(userId, password)).thenReturn(loginUser);
        when(userService.isExistUser(userId)).thenReturn(true);

        basicAuthInterceptor.preHandle(request, null, null);

        softly.assertThat(request.getSession()).isNotNull();
    }

    private MockHttpServletRequest jwtAuthHttpRequest(String userId) {
        String token = tokenAuthenticationService.toJwtByUserId(userId);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        return request;
    }
}
