package org.example.service;

import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.repository.PostRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    // сервис завязан на интерфейс, а не на конкретную реализацию
    private final PostRepositoryInterface repository;

    public PostService(PostRepositoryInterface repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(() ->
                new NotFoundException("Post with id=" + id + " is not found"));
    }

    public Post save(Post post) {
        if (post.getId() <= repository.all().size()) {
            return repository.save(post);
        }
        throw new NotFoundException("Post with id=" + post.getId() + " is not found");
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}
