package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    public PostService(PostRepository postRepository, PostTagRepository postTagRepository) {
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
    }

    public long getTotalCount() {
        return postRepository.count();
    }

    public List<PostListDto> getPostsPage(int page, int size) {
        int offset = (page - 1) * size;
        List<Post> posts = postRepository.findPage(offset, size);
        return posts.stream()
                .map(PostListDto::from)
                .toList();
    }

    @Transactional
    public PostResponseDTO getPost(Long id) {
        // JPA의 findById를 통해 영속 상태로 조회. 트랜잭션 내에서 views를 변경하면 Dirty Checking으로 자동 업데이트됨.
        Post post = postRepository.findById(id);
        if (post != null) {
            postRepository.incrementViews(id);
            return PostResponseDTO.from(post);
        }
        return null;
    }

    @Transactional
    public void createPost(PostCreateDto createDto) {
        // 엔티티를 생성하고 영속화함
        Post post = createDto.toEntity();
        postRepository.save(post);
        saveTags(post.getId(), createDto.tags());
    }

    @Transactional
    public void updatePost(Long id, PostUpdateDto updateDto) {
        // 조회 후 필드를 변경하면 트랜잭션 종료 시점에 변경 감지가 동작하여 UPDATE 쿼리가 실행됨
        Post post = postRepository.findById(id);
        if (post != null) {
            updateDto.updateEntity(post);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.update(post);
            postTagRepository.deleteByPostNo(id);
            saveTags(id, updateDto.tags());
        }
    }

    public PostUpdateDto getPostForEdit(Long id) {
        Post post = postRepository.findById(id);
        if (post == null)
            return null;
        String tags = postTagRepository.findByPostNo(id).stream()
                .map(PostTag::getTagName)
                .collect(java.util.stream.Collectors.joining(", "));
        return new com.example.vibeapp.post.dto.PostUpdateDto(post.getTitle(), post.getContent(), tags);
    }

    public List<PostTag> getTagsByPostId(Long postId) {
        return postTagRepository.findByPostNo(postId);
    }

    @Transactional
    public void deletePost(Long id) {
        // 벌크 삭제 및 엔티티 삭제를 위해 트랜잭션 필요
        postTagRepository.deleteByPostNo(id);
        postRepository.deleteById(id);
    }

    private void saveTags(Long postId, String tags) {
        if (tags == null || tags.isBlank())
            return;
        for (String raw : tags.split(",")) {
            String tagName = raw.trim();
            if (!tagName.isEmpty()) {
                PostTag postTag = new PostTag();
                postTag.setPostNo(postId);
                postTag.setTagName(tagName);
                postTagRepository.save(postTag);
            }
        }
    }
}
