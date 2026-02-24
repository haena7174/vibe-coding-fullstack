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

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
    }

    public void updatePost(Long id, PostUpdateDto updateDto) {
        Post post = postRepository.findById(id);
        if (post != null) {
            updateDto.updateEntity(post);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.update(post);
        }
    }

    public PostUpdateDto getPostForEdit(Long id) {
        Post post = postRepository.findById(id);
        return (post != null) ? PostUpdateDto.from(post) : null;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
