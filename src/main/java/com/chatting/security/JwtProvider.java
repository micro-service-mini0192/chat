package com.chatting.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chatting.domain.members.domain.Member;
import com.chatting.domain.members.domain.MemberDetails;
import com.chatting.exception.AuthedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${security.key}")
    public String SECRET;

    public final String REFRESH_HEADER_STRING = "Refresh";
    public final String TOKEN_PREFIX_JWT = "Bearer ";
    public final String JWT_HEADER_STRING = "Authorization";

    private Member decodeToken(String token, String key) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(key))
                .build()
                .verify(token);

        Long id = decodedJWT.getClaim("id").asLong();
        String username = decodedJWT.getClaim("username").asString();
        String nickname = decodedJWT.getClaim("nickname").asString();

        return Member.builder()
                .id(id)
                .username(username)
                .nickname(nickname)
                .build();
    }

    /**
     * 인가 진행
     * @param memberDetails
     */
    public void validToken(MemberDetails memberDetails) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * JWT 복호화
     * @param token JWT 정보
     * @return MemberDetails
     */
    public MemberDetails getMemberDetails(String token) {
        token = token.replace(TOKEN_PREFIX_JWT, "");
        if(token.isEmpty()) throw new AuthedException("Token is missing");
        try {
            Member jwtMember = decodeToken(token, SECRET);
            return new MemberDetails(jwtMember);
        } catch(Exception e) {
            throw new SecurityException("Invalid or expired token");
        }
    }
}

