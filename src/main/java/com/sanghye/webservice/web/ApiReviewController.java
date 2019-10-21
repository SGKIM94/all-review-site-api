package com.sanghye.webservice.web;

import com.sanghye.webservice.CannotDeleteException;
import com.sanghye.webservice.domain.Answer;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.security.LoginUser;
import com.sanghye.webservice.service.ReviewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/review")
public class ApiReviewController {
    @Resource(name = "reviewService")
    private ReviewService reviewService;
    private static final String API_REVIEW_LOCATION = "/api/reviews/";

    @PostMapping("")
    public ResponseEntity<Void> create(@LoginUser User loginUser, @Valid @RequestBody Answer answer) {
        Answer saveAnswer = reviewService.createAnswer(loginUser, answer);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(API_REVIEW_LOCATION + saveAnswer.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Answer add(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody String contents) {
        return reviewService.addAnswer(loginUser, id, contents);
    }

    @DeleteMapping("/{id}")
    public void delete(@LoginUser User loginUser, @PathVariable long id) throws CannotDeleteException {
        reviewService.deleteAnswer(loginUser, id);
    }
}
