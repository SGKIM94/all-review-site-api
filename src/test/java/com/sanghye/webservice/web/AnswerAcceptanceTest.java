package com.sanghye.webservice.web;

import com.sanghye.webservice.domain.Question;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.support.domain.HtmlFormDataBuilder;
import com.sanghye.webservice.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

public class AnswerAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);


    @Test
    public void add() throws Exception {
        final User loginUser = defaultUser();
        Optional<Question> question = defaultQuestion();

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("user", loginUser)
                .addParameter("questionId", question.orElseThrow(IllegalArgumentException::new).getId())
                .addParameter("contents", "first answer")
                .build();

        softly.assertThat(foundResource(getAnswerPath(""), request)).startsWith("/questions");
    }

    @Test
    public void delete() throws Exception {
        final User loginUser = defaultUser();

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .addParameter("loginUser", loginUser)
                .build();

        softly.assertThat(foundResource(getAnswerPath("/%d"), request)).startsWith("/questions");
    }
}
