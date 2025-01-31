package com.chatting.security.refreshToken.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RefreshTokenRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(RefreshToken refreshToken) {
        if(em.find(RefreshToken.class, refreshToken.getId()) != null) {
            em.merge(refreshToken);
            return;
        }
        em.persist(refreshToken);
    }

    public void delete(Long id) {
        RefreshToken refreshToken = em.find(RefreshToken.class, id);
        if(refreshToken != null) {
            em.remove(refreshToken);
        }
    }
}
