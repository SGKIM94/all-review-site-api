package com.sanghye.webservice.dto.user;

import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.support.test.BaseTest;
import org.junit.Test;

public class UserLoginRequestDtoTest extends BaseTest {

    @Test
    public void 로그인_요청시_아이디_와_패스워드를_정상적으로_생성하는가() {
        //given
        String userId = "sanggu";
        String password = "123456";

        //when
        User user = new UserLoginRequestDto().toEntity(userId, password);

        //then
        softly.assertThat(user.getUserId()).isEqualTo("sanggu");
        softly.assertThat(user.getPassword()).isEqualTo("123456");
    }
}
