package com.chatting.domain.members.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public void save(Member member) {
        Member existMember = findById(member.getId()).orElse(null);
        if(existMember == null) {
            em.persist(member);
            return;
        }
        if(!existMember.equals(member)) {
            em.merge(member);
        }
    }
}
