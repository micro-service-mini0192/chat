package com.chatting.domain.room.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

    public Optional<RoomMember> findRoomMemberOfRoomAndMember(Long roomId, Long memberId) {
        try {
            RoomMember roomMembers = em.createQuery("SELECT rm FROM RoomMember rm WHERE rm.room.id = :roomId AND rm.member.id = :memberId", RoomMember.class)
                    .setParameter("roomId", roomId)
                    .setParameter("memberId", memberId)
                    .setMaxResults(1)
                    .getSingleResult();
            return Optional.of(roomMembers);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<RoomMember> findMemberBelongRooms(Long memberId) {
        return em.createQuery("SELECT rm FROM RoomMember rm WHERE rm.member.id = :memberId", RoomMember.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public boolean isMemberBelongRoom(Long roomId, Long memberId) {
        List<RoomMember> roomMember = em.createQuery("SELECT rm FROM RoomMember rm WHERE rm.room.id = :roomId AND rm.member.id = :memberId", RoomMember.class)
                .setParameter("roomId", roomId)
                .setParameter("memberId", memberId)
                .getResultList();
        return !roomMember.isEmpty();
    }
}
