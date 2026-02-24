package com.example.vibeapp.post;

import jakarta.persistence.*;

@Entity
@Table(name = "POST_TAGS")
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "POST_NO", nullable = false)
    private Long postNo;

    @Column(name = "TAG_NAME", nullable = false, length = 50)
    private String tagName;

    public PostTag() {
    }

    public PostTag(Long id, Long postNo, String tagName) {
        this.id = id;
        this.postNo = postNo;
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostNo() {
        return postNo;
    }

    public void setPostNo(Long postNo) {
        this.postNo = postNo;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "PostTag{" +
                "id=" + id +
                ", postNo=" + postNo +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
