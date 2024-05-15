package dev.ubaid.ssbwj.service.impl;

import dev.ubaid.ssbwj.domain.Post;
import dev.ubaid.ssbwj.repository.PostRepository;
import dev.ubaid.ssbwj.service.PostService;
import dev.ubaid.ssbwj.service.dto.PostDTO;
import dev.ubaid.ssbwj.service.mapper.PostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PostServiceImpl implements PostService {
    
    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public List<PostDTO> getAll() {
        log.info("###############################Get All Posts#########################################");
        return postRepository
                .findAll()
                .stream()
                .map(postMapper::toDTO)
                .peek(post -> log.info("#############################################Post: {}", post))
                .toList();
    }

    @Override
    public PostDTO findByUuid(String uuid) {
        return postRepository
                .findByUuid(uuid)
                .map(postMapper::toDTO)
                .orElseThrow();
    }

    @Override
    public PostDTO save(PostDTO postDTO) {
        Post post = postMapper.toEntity(postDTO);
        return Optional
                .of(postRepository.save(post))
                .map(postMapper::toDTO)
                .orElseThrow();
    }
}
