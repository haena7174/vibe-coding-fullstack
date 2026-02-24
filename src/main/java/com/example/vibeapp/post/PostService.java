package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import org.springframework.stereotype.Service;

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

    public PostResponseDTO getPost(Long id) {
        Post post = postRepository.findById(id);
        if (post != null) {
            postRepository.incrementViews(id);
            return PostResponseDTO.from(post);
        }
        return null;
    }

    public void createPost(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        postRepository.save(post);
        saveTags(post.getId(), createDto.tags());
    }

    public void updatePost(Long id, PostUpdateDto updateDto) {
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
        return (post != null) ? PostUpdateDto.from(post) : null;
    }

    public List<PostTag> getTagsByPostId(Long postId) {
        return postTagRepository.findByPostNo(postId);
    }

    public void deletePost(Long id) {
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
