package com.sanghye.webservice.dto.user;

import com.sanghye.webservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginResponseDto {
    private String email;
    private String password;
    private String token;
    private String userId;

    @Builder
    public UserLoginResponseDto(String email, String password, String token, String userId) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.userId = userId;
    }

    User toEntity(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

    public UserLoginResponseDto toDtoEntity(String email, String password, String token, String userId) {
        return UserLoginResponseDto.builder()
                .email(email)
                .password(password)
                .token(token)
                .userId(userId)
                .build();
    }

    public String getUserId() {
        return this.email.split("@")[0];
    }
}
