package com.chatting.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chatting.domain.member.domain.Member;
import com.chatting.domain.member.domain.MemberDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    public final String SECRET = "test";

    public final int JWT_EXPIRATION_TIME = 60 * 60 * 1000;
    public final int REFRESH_EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    public final String REFRESH_HEADER_STRING = "Refresh";
    public final String TOKEN_PREFIX_JWT = "Bearer ";
    public final String JWT_HEADER_STRING = "Authorization";

    public String createJwtToken(MemberDetails member) {

        String role = member.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0);

        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .withClaim("id", member.getMemberId())
                .withClaim("nickname", member.getNickname())
                .withClaim("role", role)
                .sign(Algorithm.HMAC512(SECRET));

    }

    private String createRefreshToken(MemberDetails member, String createTime) {

        String role = member.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0);

        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .withClaim("id", member.getMemberId())
                .withClaim("nickname", member.getNickname())
                .withClaim("role", role)
                .sign(Algorithm.HMAC512(createTime));

    }

    private Member decodeToken(String token, String key) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(key))
                .build()
                .verify(token);

        Long id = decodedJWT.getClaim("id").asLong();
        String nickname = decodedJWT.getClaim("nickname").asString();
        String role = decodedJWT.getClaim("role").toString().replace("\"", "");

        return Member.builder()
                .id(id)
                .nickname(nickname)
                .role(role)
                .build();
    }

    public void validToken(String token) {
        if(token.isEmpty()) throw new SecurityException("Not Found Token");
        try {
            MemberDetails jwtTokenMemberDetails = getMemberDetails(token);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(jwtTokenMemberDetails, null, jwtTokenMemberDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(Exception e) {
            throw new SecurityException("Invalid or expired token");
        }
    }

    public MemberDetails getMemberDetails(String token) {
        token = token.replace(TOKEN_PREFIX_JWT, "");
        Member jwtMember = decodeToken(token, SECRET);
        return new MemberDetails(jwtMember);
    }
}
