package com.chatting.security.refreshToken.application;

import com.chatting.security.refreshToken.domain.RefreshToken;
import com.chatting.security.refreshToken.domain.RefreshTokenRepository;
import com.chatting.security.refreshToken.presentation.RefreshTokenRequest;
import com.chatting.security.refreshToken.presentation.RefreshTokenState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void refreshTokenSaveManager(RefreshTokenRequest refreshTokenRequest) {
        if(refreshTokenRequest.getState() == RefreshTokenState.LOGIN) {
            refreshTokenLoginState(refreshTokenRequest);
            return;
        }

        if(refreshTokenRequest.getState() == RefreshTokenState.LOGOUT) {
            refreshTokenLogoutState(refreshTokenRequest.getId());
            return;
        }
    }

    private void refreshTokenLoginState(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = RefreshToken.builder()
                .id(refreshTokenRequest.getId())
                .username(refreshTokenRequest.getUsername())
                .nickname(refreshTokenRequest.getNickname())
                .token(refreshTokenRequest.getToken())
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    private void refreshTokenLogoutState(Long id) {
        refreshTokenRepository.delete(id);
    }
}
