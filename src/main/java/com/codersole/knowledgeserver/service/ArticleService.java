package com.codersole.knowledgeserver.service;

import com.codersole.knowledgeserver.dto.ArticleCreateDTO;
import com.codersole.knowledgeserver.dto.ArticleQueryDTO;
import com.codersole.knowledgeserver.dto.ArticleUpdateDTO;
import com.codersole.knowledgeserver.entity.Article;
import com.codersole.knowledgeserver.vo.ArticleVO;
import com.codersole.knowledgeserver.vo.PageVO;

public interface ArticleService {
    ArticleVO create(ArticleCreateDTO dto);

    PageVO<ArticleVO> page(ArticleQueryDTO query);

    ArticleVO update(Long id,ArticleUpdateDTO dto);

    void delete(Long id);

    ArticleVO getById(Long id);
}
