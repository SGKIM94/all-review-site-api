package com.sanghye.webservice.domain;

import com.sanghye.webservice.exception.UnAuthorizedException;
import org.hibernate.annotations.Where;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sanghye.webservice.domain.ContentType.ANSWER;

@Embeddable
public class Answers {
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers;

    Answers() {
        this.answers = new ArrayList<>();
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    Answer get(int index) {
        return answers.get(index);
    }

    void checkAnswerOwner(User loginUser) {
        for (Answer answer : answers) {
            isOwnerLoginUser(loginUser, answer);
        }
    }

    private void isOwnerLoginUser(User loginUser, Answer answer) {
        if (!answer.isOwner(loginUser)) {
            throw new UnAuthorizedException("답변에 다른 작성자가 있어 삭제할 수 없습니다.");
        }
    }

    List<DeleteHistory> deleteAll(User loginUser) {
        List<DeleteHistory> deleteHistories = new ArrayList<>();

        for (Answer answer : this.answers) {
            checkAnswerOwner(loginUser);
            answer.deleteAnswer(loginUser);
            deleteHistories.add(new DeleteHistory(ANSWER, answer.getId(), loginUser, LocalDateTime.now()));
        }

        return deleteHistories;
    }
}
