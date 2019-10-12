package com.sanghye.webservice.fixtures;

import static com.sanghye.webservice.fixtures.Question.newQuestion;
import static com.sanghye.webservice.fixtures.User.JAVAJIGI;
import static com.sanghye.webservice.fixtures.User.SANGGU;

public class Answer {
    public static final com.sanghye.webservice.domain.Answer newAnswer(long id, String contents) {
        return new com.sanghye.webservice.domain.Answer(id, JAVAJIGI, newQuestion("title", "contensts"), contents);
    }

    public static final com.sanghye.webservice.domain.Answer newAnswer(String contents) {
        return new com.sanghye.webservice.domain.Answer(0L, JAVAJIGI, newQuestion("title", "contensts"), contents);
    }

    public static final com.sanghye.webservice.domain.Answer newAnswerAnotherUser(String contents) {
        return new com.sanghye.webservice.domain.Answer(0L, SANGGU, newQuestion("title", "contensts"), contents);
    }
}
