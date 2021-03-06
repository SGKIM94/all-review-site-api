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
public class RegisterRequestDto {
    private String title;
    private String contents;
    private User writer;

    @Builder
    public RegisterRequestDto(String title, String contents, User writer) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
    }

    public Question toEntity(String title, String contents) {
        return Question.builder()
                .title(title)
                .contents(contents)
                .build();
    }

    public static RegisterRequestDto toDtoEntity(String title, String contents, User writer) {
        return RegisterRequestDto.builder()
                .title(title)
                .contents(contents)
                .writer(writer)
                .build();
    }
}
