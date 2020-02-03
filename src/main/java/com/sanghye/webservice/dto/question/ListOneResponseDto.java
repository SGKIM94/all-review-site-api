package com.sanghye.webservice.dto.question;

import com.sanghye.webservice.domain.Question;
import com.sanghye.webservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListOneResponseDto {
    private String title;
    private String contents;
    private User writer;
    private String createAt;
    private String updateAt;

    @Builder
    public ListOneResponseDto(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
        this.writer = question.getWriter();
        this.createAt = question.getFormattedCreateDate();
        this.updateAt = question.getFormattedModifiedDate();
    }

    public static ListOneResponseDto toDtoEntity(Question question) {
        return ListOneResponseDto.builder()
                .question(question)
                .build();
    }
}
