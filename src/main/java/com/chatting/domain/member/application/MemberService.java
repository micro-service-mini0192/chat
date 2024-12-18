package com.chatting.domain.member.application;

import com.chatting.domain.member.domain.Member;
import com.chatting.domain.member.domain.MemberRepository;
import com.chatting.domain.member.presentation.dto.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(MemberRequest.MemberSave dto) {
        String password = passwordEncoder.encode(dto.password());
        Member member = MemberRequest.MemberSave.toEntity(dto, password);
        if(member == null) throw new RuntimeException("값을 불러 올 수 없습니다.");
        memberRepository.save(member);
    }
}
