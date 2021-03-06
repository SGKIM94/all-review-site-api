package com.sanghye.webservice.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanghye.webservice.domain.Question;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.support.domain.BaseResponse;
import com.sanghye.webservice.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;

import java.util.*;

import static com.sanghye.webservice.fixtures.Question.newQuestion;

public class ApiQuestionAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiQuestionAcceptanceTest.class);
    private static final String TITLE = "제목 내용";
    private static final String CONTENTS = "본문 내용";
    private static final String API_QUESTION_LOCATION = "/api/questions";
    private static final String API_QUESTION_LIST_LOCATION = "/api/questions/list";

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void create() {
        User loginUser = defaultUser();

        Question question = newQuestion(TITLE, CONTENTS, loginUser);

        ResponseEntity<Void> response = jwtAuthTemplate().postForEntity(API_QUESTION_LOCATION, question, Void.class);
        Question dbQuestion = jwtAuthTemplate().getForObject(API_QUESTION_LOCATION, Question.class);

        softly.assertThat(dbQuestion).isNotNull();
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @Test
    public void update() {
        User loginUser = defaultUser();

        String location = createLocation(loginUser);

        Question original = getResource(location, Question.class, loginUser);
        Question updateQuestion = new Question(original.getId(), original.getTitle(), original.getContents(), loginUser);

        ResponseEntity<Question> responseEntity =
                jwtAuthTemplate().exchange(location, HttpMethod.PUT, createHttpEntity(updateQuestion), Question.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(updateQuestion.equals(responseEntity.getBody())).isTrue();
    }

    @Test
    public void update_no_login() {
        User loginUser = defaultUser();
        String location = createLocation(loginUser);

        Question original = getResource(location, Question.class, loginUser);
        Question updateQuestion = new Question(original.getId(), original.getTitle(), original.getContents(), loginUser);

        ResponseEntity<Question> responseEntity =
                template().exchange(location, HttpMethod.PUT,
                        createNoAuthorizationHttpEntity(updateQuestion), Question.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        log.debug("error message : {}", responseEntity.getBody());
    }

    @Test
    public void delete() {
        User loginUser = defaultUser();

        String location = createLocation(loginUser);
        Question original = getResource(location, Question.class, loginUser);

        ResponseEntity<Void> responseEntity
                = jwtAuthTemplate().exchange(location, HttpMethod.DELETE, createHttpEntity(original), Void.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void list_조회시_데이터가_정상적으로_리턴되는지() {
        User loginUser = defaultUser();

        String location = createLocation(loginUser);
        Question original = getResource(location, Question.class, loginUser);

        ResponseEntity<BaseResponse> responseEntity
                = jwtAuthTemplate()
                .exchange(API_QUESTION_LIST_LOCATION, HttpMethod.GET, createHttpEntity(original), BaseResponse.class);

        Map<String, Object> question = getFirstQuestionsFromBody(responseEntity);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(question).isNotNull();
        softly.assertThat(question.get("writer").getClass()).isNotNull();
    }

    private Map<String, Object> getFirstQuestionsFromBody(ResponseEntity<BaseResponse> responseEntity) {
        BaseResponse body = responseEntity.getBody();

        assert body != null;
        Map<String, Object> information = convertFromObjectToMap(body.getInformation());
        List<Map<String, Object>> questions = convertFromObjectToListMap(information.get("questions"));

        return convertFromObjectToMap(questions.get(0));
    }

    private Map<String, Object> convertFromObjectToMap(Object value) {
        return objectMapper.convertValue(value, new TypeReference<Map<String, Object>>() {});
    }

    private List<Map<String, Object>> convertFromObjectToListMap(Object value) {
        return objectMapper.convertValue(value, new TypeReference<List<Map<String, Object>>>(){});
    }

    private String createLocation(User loginUser) {
        Question question = newQuestion(TITLE, CONTENTS, loginUser);
        return createResource(API_QUESTION_LOCATION, question);
    }
}
