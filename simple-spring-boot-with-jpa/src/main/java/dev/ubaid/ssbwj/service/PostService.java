package dev.ubaid.ssbwj.service;

import dev.ubaid.ssbwj.service.dto.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAll();
    PostDTO findByUuid(String uuid);
    PostDTO save(PostDTO postDTO);
}
