package com.sanghye.webservice.fixtures;

import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.dto.question.RegisterRequestDto;

import static com.sanghye.webservice.fixtures.User.JAVAJIGI;

public class Question {
    public static com.sanghye.webservice.domain.Question newQuestion(String title, String contents) {
        return newQuestion(0L, title, contents, JAVAJIGI);
    }

    public static com.sanghye.webservice.domain.Question newQuestion(String title, String contents, com.sanghye.webservice.domain.User loginUser) {
        return newQuestion(0L, title, contents, loginUser);
    }

    public static com.sanghye.webservice.domain.Question newQuestion(long id, String title, String contents, User loginUser) {
        return new com.sanghye.webservice.domain.Question(id, title, contents, loginUser);
    }

    public static RegisterRequestDto newRequestDto(com.sanghye.webservice.domain.Question question, String userid) {
        return RegisterRequestDto.toDtoEntity(question.getTitle(), question.getContents(),userid);
    }
}
