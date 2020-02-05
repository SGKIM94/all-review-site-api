package com.sanghye.webservice.service;

import com.sanghye.webservice.domain.Question;
import com.sanghye.webservice.domain.QuestionRepository;
import com.sanghye.webservice.domain.User;
import com.sanghye.webservice.domain.UserRepository;
import com.sanghye.webservice.exception.CannotDeleteException;
import com.sanghye.webservice.support.test.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.sanghye.webservice.fixtures.Question.newRequestDto;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class QuestionServiceTest extends BaseTest {
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private QnaService qnaService;

    private static final String DEFAULT_LOGIN_USER = "javajigi";
    private final Question question = new Question("question title", "nextstep contents");
    private final Question original = new Question("title", "contents");

    private final Question target = new Question("new title", "new contents");
    private final User user = new User("javajigi", "test", "자바지기", "javajigi@slipp.net");

    @Test
    public void create_question_success() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        qnaService.create(user, newRequestDto(question, user));
    }

    @Test(expected = NoSuchElementException.class)
    public void create_question_no_login() {
        when(userRepository.findByUserId(DEFAULT_LOGIN_USER)).thenReturn(Optional.empty());
        User user = userRepository.findByUserId(DEFAULT_LOGIN_USER).get();

        when(questionRepository.findById(user.getId())).thenReturn(Optional.of(question));

        qnaService.create(user, newRequestDto(question, user));
    }

    @Test
    public void update_question_success() {
        when(questionRepository.findById(user.getId())).thenReturn(Optional.of(original));
        when(questionRepository.findById(original.getId())).thenReturn(Optional.of(original));

        original.writeBy(user);
        target.writeBy(user);

        qnaService.update(user, target.getId(), target);
        softly.assertThat(original.getTitle()).isEqualTo(target.getTitle());
        softly.assertThat(original.getContents()).isEqualTo(target.getContents());
    }

    @Test(expected = NullPointerException.class)
    public void update_question_not_original_id() {
        when(questionRepository.findById(user.getId())).thenReturn(Optional.of(original));

        qnaService.update(user, target.getId(), target);
    }


    @Test(expected = NullPointerException.class)
    public void update_question_is_not_owner_original() {
        when(questionRepository.findById(user.getId())).thenReturn(Optional.of(original));
        when(questionRepository.findById(original.getId())).thenReturn(Optional.of(original));

        target.writeBy(user);

        qnaService.update(user, target.getId(), target);
    }

    @Test
    public void update_question_what_deleted_original() {
        when(questionRepository.findById(user.getId())).thenReturn(Optional.of(original));
        when(questionRepository.findById(original.getId())).thenReturn(Optional.of(original));

        target.writeBy(user);
        original.writeBy(user);

        qnaService.update(user, target.getId(), target);
        softly.assertThat(original.isDeleted()).isEqualTo(true);
    }

    @Test
    public void delete_question_success() throws CannotDeleteException {
        when(questionRepository.findById(user.getId())).thenReturn(Optional.of(question));
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        question.writeBy(user);

        qnaService.deleteQuestion(user, question.getId());
        softly.assertThat(question.isDeleted()).isEqualTo(true);
    }

    @Test(expected = NullPointerException.class)
    public void delete_question_is_not_exist_owner() throws CannotDeleteException {
        when(questionRepository.findById(user.getId())).thenReturn(Optional.of(question));
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        qnaService.deleteQuestion(user, question.getId());
        softly.assertThat(question.isDeleted()).isEqualTo(true);
    }
}
