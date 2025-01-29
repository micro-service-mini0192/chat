package com.chatting.security;

import com.chatting.domain.members.domain.Member;
import com.chatting.domain.members.domain.MemberDetails;
import com.chatting.exception.AuthedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof MemberDetails memberDetails) {
            return memberDetails.getMember();
        }
        throw new AuthedException("인증되지 않은 사용자 입니다.");
    }
}
