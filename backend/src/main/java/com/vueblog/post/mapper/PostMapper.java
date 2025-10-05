package com.vueblog.post.mapper;

import com.vueblog.post.domain.Post;
import com.vueblog.post.web.dto.request.CreatePostRequest;
import com.vueblog.post.web.dto.response.PostDTO;
import com.vueblog.writer.mapper.WriterMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = WriterMapper.class
)
public interface PostMapper {

    PostDTO toDto(Post post);

    Post toEntity(CreatePostRequest request);

}
