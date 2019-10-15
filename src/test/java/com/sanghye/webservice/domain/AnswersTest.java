package com.sanghye.webservice.domain;

import com.sanghye.webservice.support.test.BaseTest;
import org.junit.Test;

import java.util.List;

import static com.sanghye.webservice.fixtures.Answer.newAnswer;
import static com.sanghye.webservice.fixtures.User.JAVAJIGI;


public class AnswersTest extends BaseTest {
    static final int FIRST_INDEX = 0;
    final Answer answer = newAnswer("contents");
    final Answers answers = new Answers();

    @Test
    public void add_test() {
        answers.add(answer);
        softly.assertThat(answers.get(FIRST_INDEX)).isEqualTo(answer);
    }

    @Test
    public void delete_all_test() {
        answers.add(answer);
        List<DeleteHistory> deleteHistories = answers.deleteAll(JAVAJIGI);

        softly.assertThat(deleteHistories.toString()).contains(JAVAJIGI.getName());
    }
}
