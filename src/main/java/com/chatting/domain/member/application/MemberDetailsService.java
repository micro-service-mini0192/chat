package com.chatting.domain.member.application;

import com.chatting.domain.member.domain.Member;
import com.chatting.domain.member.domain.MemberDetails;
import com.chatting.domain.member.domain.MemberRepository;
import com.chatting.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow();
        return new MemberDetails(member);
    }
}
