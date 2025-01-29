package com.chatting.domain.members.domain;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public void save(Member member) {
        Member existMember = findById(member.getId());
        if(existMember == null) {
            em.persist(member);
            return;
        }
        if(!existMember.equals(member)) {
            em.merge(member);
        }
    }
}
