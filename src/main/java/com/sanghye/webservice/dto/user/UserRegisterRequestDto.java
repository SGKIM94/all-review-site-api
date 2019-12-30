package com.sanghye.webservice.dto.user;

import com.sanghye.webservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//TODO : 회원 가입 시 값 받는 DTO 만들기
public class UserRegisterRequestDto {
    private String email;
    private String password;
    private String name;
    private String mobile;
    private String passwordConfirm;

    @Builder
    public UserRegisterRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    User toEntity(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

    public UserRegisterRequestDto toDtoEntity(String email, String password) {
        return UserRegisterRequestDto.builder()
                .email(email)
                .password(password)
                .build();
    }

    public String getUserId() {
        return this.email.split("@")[0];
    }
}
