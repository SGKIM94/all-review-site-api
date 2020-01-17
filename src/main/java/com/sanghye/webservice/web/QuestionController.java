package com.sanghye.webservice.web;

import com.sanghye.webservice.dto.question.RegisterRequestDto;
import com.sanghye.webservice.exception.CannotDeleteException;
import com.sanghye.webservice.domain.Question;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.security.LoginUser;
import com.sanghye.webservice.service.QnaService;
import com.sanghye.webservice.support.domain.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "qnaService")
    private QnaService qnaService;

    @GetMapping("/form")
    public String form() {
        return "/qna/form";
    }

    @PostMapping("")
    public String create(@LoginUser User user, RegisterRequestDto question) {
        qnaService.create(user, question);
        return "redirect:/questions/form";
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse> list(Model model, Pageable pageable) {
        List<Question> questions = qnaService.findAll(pageable);
        log.debug("question size : {}", questions.size());

        model.addAttribute("questions", questions);
        return new ResponseEntity<>(new BaseResponse(model), HttpStatus.OK);
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("question", qnaService.findById(id));
        return "/qna/show";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, Question target) {
        qnaService.update(loginUser, id, target);
        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) throws CannotDeleteException {
        qnaService.deleteQuestion(loginUser, id);
        return "redirect:/questions";
    }
}
