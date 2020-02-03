package com.sanghye.webservice.dto.question;

import com.sanghye.webservice.domain.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListResponseDto {
    private List<ListOneResponseDto> questions;
    
    @Builder
    public ListResponseDto(List<ListOneResponseDto> questions) {
        this.questions = questions;
    }

    public static ListResponseDto toDtoEntity(List<Question> questions) {
        List<ListOneResponseDto> dto = new ArrayList<>();
        questions.stream()
                .map(question -> dto.add(ListOneResponseDto.toDtoEntity(question)))
                .close();

        return ListResponseDto.builder()
                .questions(dto)
                .build();
    }
}
