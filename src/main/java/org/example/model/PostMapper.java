package org.example.model;

public class PostMapper {
    public static Post toPost(PostDTO postDTO) {
        return new Post(postDTO.getId(), postDTO.getContent());
    }

    public static PostDTO toPostDTO (Post post) {
        return new PostDTO(post.getId(), post.getContent());
    }
}
