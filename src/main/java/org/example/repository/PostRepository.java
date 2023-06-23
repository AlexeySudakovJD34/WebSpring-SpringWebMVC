package org.example.repository;

import org.example.model.Post;
import org.springframework.stereotype.Repository;

//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository implements PostRepositoryInterface {
    private final ConcurrentHashMap<Long, Post> repo = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Post> all() {
//        return new ArrayList<>(repo.values().stream().filter(x -> !x.isDeleted()).toList());
        return repo.values().stream().filter(x -> !x.isDeleted()).collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        Post postById = repo.get(id);
        return Optional.ofNullable(postById.isDeleted() ? null : postById);
    }

    public Post save(Post post) {
        long postId = post.getId();
        if (postId == 0) {
            long newPostId = idCounter.getAndIncrement();
            post.setId(newPostId);
            repo.put(newPostId,post);
        } else {
            if (repo.containsKey(postId) && !repo.get(postId).isDeleted()) {
                repo.put(postId, post);
            }
        }
        return post;
    }

    public void removeById(long id) {
        Post postToRemove = repo.get(id);
        if (postToRemove != null) {
            postToRemove.setDeleted(true);
        }
    }
}
