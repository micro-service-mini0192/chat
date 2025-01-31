package com.chatting.security.refreshToken.application;

import com.chatting.domain.members.domain.Member;
import com.chatting.domain.members.domain.MemberRepository;
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
    private final MemberRepository memberRepository;

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
        Member member = Member.builder()
                .id(refreshTokenRequest.getId())
                .username(refreshTokenRequest.getUsername())
                .nickname(refreshTokenRequest.getNickname())
                .build();

        memberRepository.save(member);

        RefreshToken refreshToken = RefreshToken.builder()
                .id(refreshTokenRequest.getId())
                .token(refreshTokenRequest.getToken())
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    private void refreshTokenLogoutState(Long id) {
        refreshTokenRepository.delete(id);
    }
}
