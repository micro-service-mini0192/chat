package com.chatting.domain.chat.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Chat chat) {
        em.persist(chat);
    }
}
