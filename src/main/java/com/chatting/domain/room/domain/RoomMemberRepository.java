package com.chatting.domain.room.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public boolean isMemberBelongRoom(Long roomId, Long memberId) {
        List<RoomMember> roomMember = em.createQuery("SELECT rm FROM RoomMember rm WHERE rm.room.id = :roomId AND rm.member.id = :memberId", RoomMember.class)
                .setParameter("roomId", roomId)
                .setParameter("memberId", memberId)
                .getResultList();
        return !roomMember.isEmpty();
    }
}
