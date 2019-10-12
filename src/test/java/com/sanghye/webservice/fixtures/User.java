package com.sanghye.webservice.fixtures;

public class User {
    public static final com.sanghye.webservice.domain.User JAVAJIGI = new com.sanghye.webservice.domain.User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public final static com.sanghye.webservice.domain.User SANGGU = newUser("sangu", "1234");

    public static com.sanghye.webservice.domain.User newUser(Long id) {
        return new com.sanghye.webservice.domain.User(id, "userId", "pass", "name", "javajigi@slipp.net");
    }

    public static com.sanghye.webservice.domain.User newUser(String userId) {
        return newUser(userId, "password");
    }

    public static com.sanghye.webservice.domain.User newUser(String userId, String password) {
        return new com.sanghye.webservice.domain.User(0L, userId, password, "name", "javajigi@slipp.net");
    }
}
