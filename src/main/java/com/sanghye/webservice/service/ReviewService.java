package com.sanghye.webservice.service;

import com.sanghye.webservice.CannotDeleteException;
import com.sanghye.webservice.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service("reviewService")
public class ReviewService {
    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);

    @Resource(name = "reviewRepository")
    private ReviewRepository reviewRepository;

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    public Review create(User loginUser, Review review) {
        review.writeBy(loginUser);
        log.debug("review : {}", review);
        return reviewRepository.save(review);
    }

    public Review findById(long id) {
        return reviewRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public Review update(User loginUser, long id, Review updatedReview) {
        Review original = findById(id);
        return original.update(loginUser, updatedReview);
    }

    @Transactional
    public List<DeleteHistory> deleteQuestion(User loginUser, long reviewId) throws CannotDeleteException {
        Review review = findById(reviewId);
        return review.delete(loginUser);
    }

    public List<Review> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable).getContent();
    }

    public Answer createAnswer(User loginUser, Answer answer) {
        answer.writeBy(loginUser);
        log.debug("review : {}", answer);
        return answerRepository.save(answer);
    }

    @Transactional
    public void deleteAnswer(User loginUser, long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        answer.orElseThrow(IllegalArgumentException::new).deleteAnswer(loginUser);
    }
}
