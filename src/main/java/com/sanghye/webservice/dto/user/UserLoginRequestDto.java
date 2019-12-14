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

    User toEntity(String userId, String password) {
        return User.builder()
                .userId(userId)
                .password(password)
                .build();
    }

    public UserLoginRequestDto toDtoEntity(String email, String password) {
        return UserLoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();
    }
}
