package com.codersole.knowledgeserver.converter;

import com.codersole.knowledgeserver.dto.ArticleCreateDTO;
import com.codersole.knowledgeserver.dto.ArticleUpdateDTO;
import com.codersole.knowledgeserver.entity.Article;
import com.codersole.knowledgeserver.vo.ArticleVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleConverter {
    Article toEntity(ArticleCreateDTO dto);
    ArticleVO toVo(Article article);

    List<ArticleVO> toVoList(List<Article> articles);

    void updateEntity(ArticleUpdateDTO dto, @MappingTarget Article article);
}
