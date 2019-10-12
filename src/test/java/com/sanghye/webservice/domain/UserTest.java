package com.sanghye.webservice.domain;

import com.sanghye.webservice.UnAuthorizedException;
import com.sanghye.webservice.support.test.BaseTest;
import org.junit.Test;

import static com.sanghye.webservice.fixtures.User.newUser;

public class UserTest extends BaseTest {
    @Test
    public void update_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = origin;
        User target = new User("sanjigi", "password", "name2", "javajigi@slipp.net2");
        origin.update(loginUser, target);
        softly.assertThat(origin.getName()).isEqualTo(target.getName());
        softly.assertThat(origin.getEmail()).isEqualTo(target.getEmail());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = newUser("javajigi");
        User target = new User("sanjigi", "password", "name2", "javajigi@slipp.net2");
        origin.update(loginUser, target);
    }

    @Test
    public void update_match_password() {
        User origin = newUser("sanjigi");
        User target = new User("sanjigi", "password", "name2", "javajigi@slipp.net2");
        origin.update(origin, target);
        softly.assertThat(origin.getName()).isEqualTo(target.getName());
        softly.assertThat(origin.getEmail()).isEqualTo(target.getEmail());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_mismatch_password() {
        User origin = newUser("sanjigi", "password");
        User target = new User("sanjigi", "password2", "name2", "javajigi@slipp.net2");
        origin.update(origin, target);
    }
}
