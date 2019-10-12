package com.sanghye.webservice.domain;

import com.sanghye.webservice.UnAuthorizedException;
import com.sanghye.webservice.support.test.BaseTest;
import org.junit.Test;

import static com.sanghye.webservice.fixtures.Answer.newAnswer;
import static com.sanghye.webservice.fixtures.User.JAVAJIGI;
import static com.sanghye.webservice.fixtures.User.SANGGU;

public class AnswerTest extends BaseTest {

    @Test
    public void delete_test() {
        Answer answer = newAnswer("댓글 내용");

        answer.deleteAnswer(JAVAJIGI);

        softly.assertThat(answer.isDeleted()).isEqualTo(true);
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_no_login_test() {
        Answer answer = newAnswer("댓글 내용");

        answer.deleteAnswer(SANGGU);
    }
}
