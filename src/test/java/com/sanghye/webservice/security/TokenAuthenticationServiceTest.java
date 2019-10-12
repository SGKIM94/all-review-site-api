package com.sanghye.webservice.security;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenAuthenticationServiceTest {
    private TokenAuthenticationService authenticationService = new TokenAuthenticationService();

    @Test
    public void token_key_가_byte_타입으로_생성되는가() {
        //given
        String salt = "63B75D39E3F6BFE72263F7C1145AC22E";

        //when
        byte[] key = authenticationService.generateKey(salt);

        //then
        assertThat(key.getClass().getName()).isEqualTo("[B");
    }
}
