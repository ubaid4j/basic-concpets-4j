package dev.ubaid.ssbwj.service.mapper;

import dev.ubaid.ssbwj.domain.Post;
import dev.ubaid.ssbwj.service.dto.PostDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
}
