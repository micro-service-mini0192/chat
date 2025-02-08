package com.chatting.domain.chat.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Chat chat) {
        em.persist(chat);
    }

    public Page<Chat> findByRoomIdPage(Long roomId, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Chat> cq = cb.createQuery(Chat.class);
        Root<Chat> from = cq.from(Chat.class);
        cq.select(from)
                .where(cb.equal(from.get("room").get("id"), roomId));

        TypedQuery<Chat> query = em.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Chat> chats = query.getResultList();
        return new PageImpl<>(chats, pageable, 0L);
    }
}
