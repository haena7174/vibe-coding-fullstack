package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * pure JPA (EntityManager) 기반 PostRepository 구현
 * MyBatis Mapper 인터페이스에서 Repository 빈으로 전환
 */
@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Post> findAll() {
        // JPQL을 사용하여 모든 게시글 조회
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public long count() {
        // JPQL을 사용하여 전체 게시글 수 조회
        return em.createQuery("select count(p) from Post p", Long.class)
                .getSingleResult();
    }

    public List<Post> findPage(int offset, int limit) {
        // 페이징 처리를 위해 setFirstResult(offset)와 setMaxResults(limit) 사용
        return em.createQuery("select p from Post p order by p.id desc", Post.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Post findById(Long id) {
        // find() 메서드를 사용하여 식별자로 엔티티 조회
        return em.find(Post.class, id);
    }

    public void save(Post post) {
        // persist()를 통해 새로운 엔티티를 영속성 컨텍스트에 저장
        em.persist(post);
    }

    public void update(Post post) {
        // merge()를 사용하여 준영속 상태의 엔티티를 영속 상태로 변경하거나,
        // 트랜잭션 내에서 변경 감지(Dirty Checking)가 동작함.
        em.merge(post);
    }

    public void deleteById(Long id) {
        // 삭제를 위해 먼저 엔티티를 조회한 후 remove() 호출
        Post post = findById(id);
        if (post != null) {
            em.remove(post);
        }
    }

    public void incrementViews(Long id) {
        // 변경 감지(Dirty Checking) 예시: 트랜잭션 내에서 엔티티를 조회하고 필드 값을 변경하면 자동 반영됨
        Post post = findById(id);
        if (post != null) {
            post.setViews(post.getViews() + 1);
        }
    }
}
