package com.vueblog.writer.mapper;

import com.vueblog.writer.domain.Writer;
import com.vueblog.writer.web.dto.request.CreateWriterRequest;
import com.vueblog.writer.web.dto.response.WriterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WriterMapper {

    WriterDTO toDTO(Writer writer);

    Writer toEntity(CreateWriterRequest request);

}
