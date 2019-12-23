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
    private String token;
    private String userId;

    @Builder
    public UserLoginResponseDto(String email, String token, String userId) {
        this.email = email;
        this.token = token;
        this.userId = userId;
    }

    public static UserLoginResponseDto toDtoEntity(User loginUser, String token) {
        return UserLoginResponseDto.builder()
                .email(loginUser.getEmail())
                .token(token)
                .userId(loginUser.getUserId())
                .build();
    }

    public String getUserId() {
        return this.email.split("@")[0];
    }
}
