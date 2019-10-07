package com.sanghye.webservice.web;

import com.sanghye.webservice.CannotDeleteException;
import com.sanghye.webservice.domain.Answer;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.security.LoginUser;
import com.sanghye.webservice.service.QnaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/answers")
public class ApiAnswerController {
    @Resource(name = "qnaService")
    private QnaService qnaService;
    private static final String API_ANSWER_LOCATION = "/api/answers/";

    @PostMapping("")
    public ResponseEntity<Void> create(@LoginUser User loginUser, @Valid @RequestBody Answer answer) {
        Answer saveAnswer = qnaService.createAnswer(loginUser, answer);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(API_ANSWER_LOCATION + saveAnswer.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Answer add(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody String contents) {
        return qnaService.addAnswer(loginUser, id, contents);
    }

    @DeleteMapping("/{id}")
    public void delete(@LoginUser User loginUser, @PathVariable long id) throws CannotDeleteException {
        qnaService.deleteAnswer(loginUser, id);
    }
}
