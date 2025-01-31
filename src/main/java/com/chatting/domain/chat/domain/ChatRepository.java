package com.chatting.domain.chat.domain;

import com.chatting.domain.members.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRepository {

    @PersistenceContext
    private EntityManager em;

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public void save(Chat chat) {
        em.persist(chat);
    }
}
