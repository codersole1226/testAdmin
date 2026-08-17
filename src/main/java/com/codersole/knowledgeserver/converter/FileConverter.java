package com.codersole.knowledgeserver.converter;

import com.codersole.knowledgeserver.entity.FileInfo;
import com.codersole.knowledgeserver.vo.FileVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileConverter {
    @Mapping(source = "filePath", target = "url")
    FileVO toVo(FileInfo fileInfo);
}
