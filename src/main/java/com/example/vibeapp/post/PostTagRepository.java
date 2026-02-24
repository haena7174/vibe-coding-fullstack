package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * pure JPA (EntityManager) 기반 PostTagRepository 구현
 */
@Repository
public class PostTagRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(PostTag postTag) {
        // 엔티티를 영속화
        em.persist(postTag);
    }

    public void deleteById(Long id) {
        // 식별자로 조회 후 삭제
        PostTag postTag = em.find(PostTag.class, id);
        if (postTag != null) {
            em.remove(postTag);
        }
    }

    public void deleteByPostNo(Long postNo) {
        // JPQL 벌크 연산으로 특정 게시글의 모든 태그 삭제
        em.createQuery("delete from PostTag pt where pt.postNo = :postNo")
                .setParameter("postNo", postNo)
                .executeUpdate();
    }

    public List<PostTag> findByPostNo(Long postNo) {
        // JPQL을 사용하여 게시글 번호로 태그 목록 조회
        return em.createQuery("select pt from PostTag pt where pt.postNo = :postNo", PostTag.class)
                .setParameter("postNo", postNo)
                .getResultList();
    }
}
