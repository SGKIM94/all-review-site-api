package com.sanghye.webservice.domain;

import com.sanghye.webservice.exception.UnAuthorizedException;
import com.sanghye.webservice.support.domain.AbstractEntity;
import com.sanghye.webservice.support.domain.UrlGeneratable;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Answer extends AbstractEntity implements UrlGeneratable {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Size(min = 5)
    @Lob
    private String contents;

    private boolean deleted = false;

    public Answer() {
    }

    @Builder
    public Answer(User writer, String contents) {
        this.writer = writer;
        this.contents = contents;
    }

    @Builder
    public Answer(Long id, User writer, Question question, String contents) {
        super(id);
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.deleted = false;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }

    public Answer setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public void writeBy(User loginUser) {
        this.writer = loginUser;
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void deleteAnswer(User user) {
        checkOwner(user);
        checkDeleted();

        this.deleted = true;
    }

    private void checkDeleted() {
        if(isDeleted()) {
            throw new IllegalArgumentException();
        }
    }

    private void checkOwner(User user) {
        if(!isOwner(user)) {
            throw new UnAuthorizedException();
        }
    }

    @Override
    public String generateUrl() {
        return String.format("%s/answers/%d", question.generateUrl(), getId());
    }

    @Override
    public String toString() {
        return "Answer [id=" + getId() + ", writer=" + writer + ", contents=" + contents + "]";
    }
}
