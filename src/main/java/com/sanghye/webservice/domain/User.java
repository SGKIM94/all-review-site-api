package com.sanghye.webservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanghye.webservice.UnAuthorizedException;
import com.sanghye.webservice.support.domain.AbstractEntity;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {
    public static final GuestUser GUEST_USER = new GuestUser();

    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String password;

    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String name;

    public User() {
    }

    @Builder
    public User(String password, String name, String email) {
        this(0L, password, name, email);
    }

    @Builder
    public User(long id, String email, String password, String name) {
        super(id);
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Builder
    public User(String email, String password) {
        super(0L);
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public void update(User loginUser, User target) {
        if (!matchUserId(loginUser.getEmail())) {
            throw new UnAuthorizedException();
        }

        if (!matchPassword(target.getPassword())) {
            throw new UnAuthorizedException();
        }

        this.name = target.name;
        this.email = target.email;
    }

    private boolean matchUserId(String email) {
        return this.email.equals(email);
    }

    public boolean matchPassword(String targetPassword) {
        return password.equals(targetPassword);
    }

    public boolean equalsNameAndEmail(User target) {
        if (Objects.isNull(target)) {
            return false;
        }

        return name.equals(target.name) &&
                email.equals(target.email);
    }

    @JsonIgnore
    public boolean isGuestUser() {
        return false;
    }

    private static class GuestUser extends User {
        @Override
        public boolean isGuestUser() {
            return true;
        }
    }

    @Override
    public String toString() {
        return "User [password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
