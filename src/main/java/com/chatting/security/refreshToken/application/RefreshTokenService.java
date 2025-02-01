package com.chatting.security.refreshToken.application;

import com.chatting.domain.members.domain.Member;
import com.chatting.domain.members.domain.MemberRepository;
import com.chatting.security.refreshToken.domain.RefreshToken;
import com.chatting.security.refreshToken.domain.RefreshTokenRepository;
import com.chatting.security.refreshToken.presentation.RefreshTokenRequest;
import com.chatting.security.refreshToken.presentation.RefreshTokenState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Transactional
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

    public void refreshTokenLoginState(RefreshTokenRequest refreshTokenRequest) {
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

    public void refreshTokenLogoutState(Long id) {
        refreshTokenRepository.delete(id);
    }
}
