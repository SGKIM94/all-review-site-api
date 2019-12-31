package com.sanghye.webservice.dto.user;

import com.sanghye.webservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterRequestDto {
    private String email;
    private String password;
    private String name;
    private String mobile;

    @Builder
    public UserRegisterRequestDto(String email, String password, String name, String mobile) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
    }

    public User toEntity(UserRegisterRequestDto dto) {
        return User.builder()
                .userId(makeUserIdByEmail(dto.getEmail()))
                .password(dto.getPassword())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public UserRegisterRequestDto toDtoEntity(String email, String password, String name, String mobile) {
        return UserRegisterRequestDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .mobile(mobile)
                .build();
    }

    public String getUserId() {
        return makeUserIdByEmail(this.email);
    }

    private String makeUserIdByEmail(String email) {
        return email.split("@")[0];
    }
}
