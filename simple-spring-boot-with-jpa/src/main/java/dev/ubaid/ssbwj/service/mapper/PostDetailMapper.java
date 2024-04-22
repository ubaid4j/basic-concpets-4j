package dev.ubaid.ssbwj.service.mapper;

import dev.ubaid.ssbwj.domain.PostDetail;
import dev.ubaid.ssbwj.service.dto.PostDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostDetailMapper extends EntityMapper<PostDetailDTO, PostDetail> {}
