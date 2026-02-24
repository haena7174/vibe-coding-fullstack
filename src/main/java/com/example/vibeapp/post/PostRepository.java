package com.example.vibeapp.post;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();

    public PostRepository() {
        // Seed data: 10 example posts
        for (long i = 1; i <= 10; i++) {
            posts.add(new Post(
                    i,
                    "Vibe Coding의 " + i + "번째 이야기",
                    "이것은 Vibe Coding의 " + i + "번째 게시글 내용입니다. 창의적인 코딩을 즐겨보세요.",
                    LocalDateTime.now().minusDays(10 - i),
                    LocalDateTime.now().minusDays(10 - i),
                    (int) (Math.random() * 100)));
        }
    }

    public List<Post> findAll() {
        return posts.stream()
                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                .toList();
    }

    public long count() {
        return posts.size();
    }

    public List<Post> findPage(int offset, int limit) {
        return posts.stream()
                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                .skip(offset)
                .limit(limit)
                .toList();
    }

    public Post findById(Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .map(post -> {
                    post.setViews(post.getViews() + 1);
                    return post;
                })
                .orElse(null);
    }

    public void save(Post post) {
        long nextId = posts.stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0L) + 1;
        post.setId(nextId);
        posts.add(post);
    }

    public void deleteById(Long id) {
        posts.removeIf(post -> post.getId().equals(id));
    }
}
