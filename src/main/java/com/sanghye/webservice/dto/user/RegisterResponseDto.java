package com.sanghye.webservice.dto.user;

import com.sanghye.webservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponseDto {
    private String email;
    private String password;
    private String name;
    private String mobile;

    @Builder
    public RegisterResponseDto(String email, String password, String name, String mobile) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
    }

    public static RegisterResponseDto toDtoEntity(User user) {
        return RegisterResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public String getUserId() {
        return makeUserIdByEmail(this.email);
    }

    private String makeUserIdByEmail(String email) {
        return email.split("@")[0];
    }
}
