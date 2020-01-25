package com.sanghye.webservice.domain;


import com.sanghye.webservice.exception.UnAuthorizedException;
import com.sanghye.webservice.support.test.BaseTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.sanghye.webservice.fixtures.Answer.newAnswer;
import static com.sanghye.webservice.fixtures.Answer.newAnswerAnotherUser;
import static com.sanghye.webservice.fixtures.Question.newQuestion;
import static com.sanghye.webservice.fixtures.User.*;

public class QuestionTest extends BaseTest {
    @Test
    public void update_test() {
        User loginUser = JAVAJIGI;

        Question question = newQuestion("제목", "내용", loginUser);
        Question target = newQuestion("수정 제목", "수정 내용", loginUser);

        question.update(loginUser, target);

        softly.assertThat(question.getTitle()).isEqualTo(target.getTitle());
        softly.assertThat(question.getContents()).isEqualTo(target.getContents());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_no_login_test() {
        User noLoginUser = newUser("sangu", "1234");

        Question question = newQuestion("제목", "내용", JAVAJIGI);
        Question target = newQuestion("수정 제목", "수정 내용", noLoginUser);

        question.update(noLoginUser, target);
    }

    @Test
    public void delete_test() {
        User loginUser = JAVAJIGI;

        Question question = newQuestion("제목", "내용", loginUser);

        question.delete(loginUser);

        softly.assertThat(question.isDeleted()).isEqualTo(true);
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_no_login_test() {
        User loginUser = JAVAJIGI;

        Question question = newQuestion("제목", "내용", loginUser);

        question.delete(SANGGU);
    }

    @Test
    public void delete_when_have_answers() {
        User loginUser = JAVAJIGI;

        Answer answer = newAnswer("삭제못하는 댓글");
        Question question = newQuestion("제목", "내용", loginUser);

        question.addAnswer(answer);
        question.delete(loginUser);

        softly.assertThat(question.isDeleted()).isEqualTo(true);
    }

    @Test(expected = UnAuthorizedException.class)
    public void cant_delete_as_answers_is_not_owner() {
        User loginUser = JAVAJIGI;

        Answer answer = newAnswerAnotherUser("삭제못하는 댓글");
        Question question = newQuestion("제목", "내용", loginUser);

        question.addAnswer(answer);
        question.delete(loginUser);
    }

    @Test
    public void delete_all_answers() {
        User loginUser = JAVAJIGI;

        Answer firstAnswer = newAnswer("첫번 째 댓글");
        Answer secondAnswer = newAnswer(1L, "두번 째 댓글");
        Question question = newQuestion("제목", "내용", loginUser);

        question.addAnswer(firstAnswer);
        question.addAnswer(secondAnswer);

        question.delete(loginUser);

        softly.assertThat(question.isDeleted()).isEqualTo(true);
        softly.assertThat(firstAnswer.isDeleted()).isEqualTo(true);
        softly.assertThat(secondAnswer.isDeleted()).isEqualTo(true);
    }

    @Test
    public void JSON_형태의_값을_Array_형태로_변환하는지() {
        //given
        List<String> originQuestion = new ArrayList<>();

        //when

        //then
        softly.assertThat(Question.convertFromJsonArrayToDoubleArray(originQuestion)).isInstanceOf(List.class);
    }
}
