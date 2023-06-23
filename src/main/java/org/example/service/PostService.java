package org.example.service;

import org.example.exception.NotFoundException;
import org.example.model.PostDTO;
import org.example.model.PostMapper;
import org.example.repository.PostRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    // сервис завязан на интерфейс, а не на конкретную реализацию
    private final PostRepositoryInterface repository;

    public PostService(PostRepositoryInterface repository) {
        this.repository = repository;
    }

    public List<PostDTO> all() {
        return repository.all().stream().map(PostMapper::toPostDTO).collect(Collectors.toList());
    }

    public PostDTO getById(long id) {
        return PostMapper.toPostDTO(repository.getById(id)
                .orElseThrow(() -> new NotFoundException("Post with id=" + id + " is not found")));
    }

    public PostDTO save(PostDTO postDTO) {
        if (postDTO.getId() <= repository.all().size()) {
            return PostMapper.toPostDTO(repository.save(PostMapper.toPost(postDTO)));
        }
        throw new NotFoundException("Post with id=" + postDTO.getId() + " is not found");
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}
