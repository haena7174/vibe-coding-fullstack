package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PostRepository {
    List<Post> findAll();

    long count();

    List<Post> findPage(@Param("offset") int offset, @Param("limit") int limit);

    Post findById(Long id);

    void save(Post post);

    void update(Post post);

    void deleteById(Long id);

    void incrementViews(Long id);
}
