package com.chatting.security;

import com.chatting.domain.members.application.MemberService;
import com.chatting.domain.members.domain.MemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwtHeader = request.getHeader(jwtProvider.JWT_HEADER_STRING);

        if(jwtHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        MemberDetails memberDetails = jwtProvider.getMemberDetails(jwtHeader);
        jwtProvider.validToken(memberDetails);
        memberService.save(memberDetails.getMember());

        filterChain.doFilter(request, response);
    }

}

