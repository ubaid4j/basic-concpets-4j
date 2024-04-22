package dev.ubaid.ssbwj.service.mapper;

import dev.ubaid.ssbwj.domain.PostComment;
import dev.ubaid.ssbwj.service.dto.PostCommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostCommentMapper extends EntityMapper<PostCommentDTO, PostComment> {
}
