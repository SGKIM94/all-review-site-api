package com.sanghye.webservice.web;


import com.sanghye.webservice.domain.Answer;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.support.test.AcceptanceTest;
import org.junit.Test;
import org.springframework.http.*;

import static com.sanghye.webservice.fixtures.Question.newQuestion;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {
    static final String TITLE = "제목 내용";
    static final String CONTENTS = "본문 내용";
    static final String API_ANSWER_LOCATION = "/api/answers";
    static final String API_QUESTION_LOCATION = "/api/questions";


    @Test
    public void show() throws Exception {
        String location = createLocation();
        Answer answer = jwtAuthTemplate().getForObject(location, Answer.class);

        softly.assertThat(answer).isNotNull();
    }

    @Test
    public void add() throws Exception {
        String location = createLocation();
        String contents = "댓글 내용";

        ResponseEntity<Answer> responseEntity =
                jwtAuthTemplate().exchange(location, HttpMethod.PUT, createHttpEntity(contents), Answer.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void delete() throws Exception {
        String location = createLocation();

        ResponseEntity<Answer> responseEntity =
                jwtAuthTemplate().exchange(location, HttpMethod.DELETE, createHttpEntity(""), Answer.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private String createLocation() {
        final User loginUser = defaultUser();
        String location = createResource(API_ANSWER_LOCATION, newQuestion(TITLE, CONTENTS, loginUser));
        createResource(API_QUESTION_LOCATION, newQuestion(TITLE, CONTENTS, loginUser));
        return location;
    }

    private HttpEntity createHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity(body, headers);
    }
}
