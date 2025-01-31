package com.chatting.domain.room.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class RoomMemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(RoomMember roomMember) {
        if (roomMember.getId() == null) {
            em.persist(roomMember);
        } else {
            em.merge(roomMember);
        }
    }
}
