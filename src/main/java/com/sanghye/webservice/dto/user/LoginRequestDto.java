package com.sanghye.webservice.dto.user;

import com.sanghye.webservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;

    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    User toEntity(String email, String password) {
        return User.LoginBuilder()
                .email(email)
                .password(password)
                .build();
    }

    public static LoginRequestDto toDtoEntity(String email, String password) {
        return LoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();
    }

    public String getUserId() {
        return this.email.split("@")[0];
    }
}
