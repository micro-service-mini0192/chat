package com.chatting.domain.member.presentation;

import com.chatting.domain.member.application.MemberService;
import com.chatting.domain.member.presentation.dto.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody MemberRequest.MemberSave dto) {
        memberService.save(dto);
        return ResponseEntity.ok().build();
    }
}
