package com.sanghye.webservice.web;

import com.sanghye.webservice.domain.Question;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.dto.question.RegisterRequestDto;
import com.sanghye.webservice.exception.CannotDeleteException;
import com.sanghye.webservice.security.LoginUser;
import com.sanghye.webservice.service.QnaService;
import com.sanghye.webservice.support.domain.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/questions")
public class ApiQuestionController {

    @Resource(name = "qnaService")
    private QnaService qnaService;

    @PostMapping("")
    public ResponseEntity<BaseResponse> create(@LoginUser User loginUser, @Valid @RequestBody RegisterRequestDto question) {
        Question saveQuestion = qnaService.create(loginUser, question);
        return new ResponseEntity<>(new BaseResponse(saveQuestion), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Question show(@PathVariable long id) {
        return qnaService.findById(id);
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
