package com.chatting.security.refreshToken.presentation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {
    Long id;
    String username;
    String nickname;
    String token;
    RefreshTokenState state;
}
