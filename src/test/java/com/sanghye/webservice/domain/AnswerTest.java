package com.sanghye.webservice.domain;

import com.sanghye.webservice.UnAuthorizedException;
import com.sanghye.webservice.support.test.BaseTest;
import org.junit.Test;

import static com.sanghye.webservice.domain.QuestionTest.SANGGU;
import static com.sanghye.webservice.domain.QuestionTest.newQuestion;
import static com.sanghye.webservice.domain.UserTest.JAVAJIGI;

public class AnswerTest extends BaseTest {

    static final Answer newAnswer(long id, String contents) {
        return new Answer(id, JAVAJIGI, newQuestion("title", "contensts"), contents);
    }

    static final Answer newAnswer(String contents) {
        return new Answer(0L, JAVAJIGI, newQuestion("title", "contensts"), contents);
    }

    static final Answer newAnswerAnoterUser(String contents) {
        return new Answer(0L, SANGGU, newQuestion("title", "contensts"), contents);
    }

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
