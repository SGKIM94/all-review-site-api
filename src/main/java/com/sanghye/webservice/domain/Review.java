package com.sanghye.webservice.domain;


import com.sanghye.webservice.UnAuthorizedException;
import com.sanghye.webservice.support.domain.AbstractEntity;
import com.sanghye.webservice.support.domain.UrlGeneratable;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static com.sanghye.webservice.domain.ContentType.QUESTION;


@Entity
public class Review extends AbstractEntity implements UrlGeneratable {
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String title;

    @Size(min = 3)
    @Lob
    private String contents;

    @Size(min = 3)
    @Lob
    private String file;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Embedded
    private Answers answers = new Answers();

    private boolean deleted = false;

    public Review() {
    }

    @Builder
    public Review(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    @Builder
    public Review(long id, String title, String contents, User loginUser) {
        super(id);
        this.title = title;
        this.contents = contents;
        this.writer = loginUser;
    }

    @Builder
    public Review(long id, String title, String contents, User loginUser, String file) {
        super(id);
        this.title = title;
        this.contents = contents;
        this.writer = loginUser;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public Review setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public Review setContents(String contents) {
        this.contents = contents;
        return this;
    }


    public void writeBy(User loginUser) {
        this.writer = loginUser;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Review update(User loginUser, Review targetQna) {
        checkOwner(loginUser);
        checkDeleted();

        this.writer = loginUser;
        this.title = targetQna.title;
        this.contents = targetQna.contents;
        this.deleted = true;

        return this;
    }

    public List<DeleteHistory> delete(User loginUser) {
        checkOwner(loginUser);
        checkDeleted();

        answers.checkAnswerOwner(loginUser);
        this.deleted = true;

        List<DeleteHistory> deleteHistories = answers.deleteAll(loginUser);
        deleteHistories.add(new DeleteHistory(QUESTION, this.getId(), loginUser, LocalDateTime.now()));
        return deleteHistories;
    }

    private void checkOwner(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException("질문자와 답변자가 같지 않습니다.");
        }
    }

    private void checkDeleted() {
        if (isDeleted()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String generateUrl() {
        return String.format("/review/%d", getId());
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}
