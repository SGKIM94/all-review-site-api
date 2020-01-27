package com.sanghye.webservice.web;

import com.sanghye.webservice.domain.Question;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.dto.question.ListResponseDto;
import com.sanghye.webservice.dto.question.RegisterRequestDto;
import com.sanghye.webservice.exception.CannotDeleteException;
import com.sanghye.webservice.security.LoginUser;
import com.sanghye.webservice.service.QnaService;
import com.sanghye.webservice.support.domain.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class ApiQuestionController {
    private static final Logger log = LoggerFactory.getLogger(ApiQuestionController.class);


    @Resource(name = "qnaService")
    private QnaService qnaService;

    @PostMapping("")
    public ResponseEntity<Void> create(@LoginUser User loginUser, @Valid @RequestBody RegisterRequestDto question) {
        Question saveQuestion = qnaService.create(loginUser, question);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/questions/" + saveQuestion.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/write")
    public ResponseEntity<BaseResponse> write(@LoginUser User loginUser, @Valid @RequestBody RegisterRequestDto question) {
        Question saveQuestion = qnaService.create(loginUser, question);
        return new ResponseEntity<>(new BaseResponse(saveQuestion), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Question show(@PathVariable long id) {
        return qnaService.findById(id);
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse> list(Pageable pageable) {
        List<Question> questions = qnaService.findAll(pageable);
        log.debug("question size : {}", questions.size());

        return new ResponseEntity<>(new BaseResponse(ListResponseDto.toDtoEntity(questions)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public Question update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody Question updateQuestion) {
        return qnaService.update(loginUser, id, updateQuestion);
    }

    @DeleteMapping("/{id}")
    public void delete(@LoginUser User loginUser, @PathVariable long id) throws CannotDeleteException {
        qnaService.deleteQuestion(loginUser, id);
    }
}
