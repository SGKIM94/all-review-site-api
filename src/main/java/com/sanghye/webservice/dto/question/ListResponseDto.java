package com.sanghye.webservice.dto.question;

import com.sanghye.webservice.domain.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListResponseDto {
    private List<Question> questions;
    
    @Builder
    public ListResponseDto(List<Question> questions) {
        this.questions = questions;
    }

    public static ListResponseDto toDtoEntity(List<Question> questions) {
        return ListResponseDto.builder()
                .questions(questions)
                .build();
    }
}
