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
    private String userId;
    private String password;
    private String token;

    @Builder
    public UserLoginRequestDto(String userId, String password, String token) {
        this.userId = userId;
        this.password = password;
        this.token = token;
    }

    User toEntity(String userId, String password) {
        return User.builder()
                .userId(userId)
                .password(password)
                .build();
    }

    public UserLoginRequestDto toDtoEntity(String userId, String password) {
        return UserLoginRequestDto.builder()
                .userId(userId)
                .password(password)
                .build();
    }
}
