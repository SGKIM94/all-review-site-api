package com.sanghye.webservice.web;


import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.support.domain.HtmlFormDataBuilder;
import com.sanghye.webservice.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class QuestionAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);
    private static final String FORMAT_PATH_VALUE_ID = "/%d";

    @Test
    public void create() throws Exception {
        User loginUser = defaultUser();
        String title = "첫 게시물";
        String contents = "첫 내용";

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", loginUser.getUserId())
                .addParameter("title", title)
                .addParameter("contents", contents)
                .build();

        softly.assertThat(foundResource(getQuestionPath(""), request)).startsWith("/questions");
    }

    @Test
    public void update() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("title", "제목수정한 것")
                .addParameter("contents", "내용 수정된 것")
                .build();

        softly.assertThat(foundResource(getQuestionPath(FORMAT_PATH_VALUE_ID), request).startsWith("/questions"));
    }

    @Test
    public void delete() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();

        ResponseEntity<String> response = jwtAuthTemplate().postForEntity(getQuestionPath(FORMAT_PATH_VALUE_ID), request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
