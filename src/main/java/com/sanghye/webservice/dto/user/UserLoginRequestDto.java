package com.sanghye.webservice.dto.user;

import com.sanghye.webservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequestDto {
    private String email;
    private String password;

    @Builder
    public UserLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    User toEntity(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

    public UserLoginRequestDto toDtoEntity(String email, String password) {
        return UserLoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();
    }

    public String getUserId() {
        return this.email.split("@")[0];
    }
}
