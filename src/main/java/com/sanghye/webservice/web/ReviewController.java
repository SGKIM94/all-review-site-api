package com.sanghye.webservice.web;


import com.sanghye.webservice.CannotDeleteException;
import com.sanghye.webservice.domain.Review;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.security.LoginUser;
import com.sanghye.webservice.service.QnaService;
import com.sanghye.webservice.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Resource(name = "reviewService")
    private ReviewService reviewService;

    @PostMapping("")
    public String add(@LoginUser User user, Review review) {
        reviewService.create(user, review);
        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) throws CannotDeleteException {
        reviewService.deleteAnswer(loginUser, id);
        return "redirect:/questions";
    }
}
