package com.chatting.domain.room.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoomRepository {

    @PersistenceContext
    private EntityManager em;

    public Room save(Room room) {
        if (room.getId() == null) {
            em.persist(room);
        } else {
            em.merge(room);
        }
        return room;
    }

    public Optional<Room> findById(Long id) {
        return Optional.ofNullable(em.createQuery("SELECT r FROM Room r WHERE r.id = :id", Room.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    public void delete(Room room) {
        em.remove(room);
    }
}
