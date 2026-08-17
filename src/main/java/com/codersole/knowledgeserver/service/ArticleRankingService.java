package com.codersole.knowledgeserver.service;

import com.codersole.knowledgeserver.vo.ArticleVO;

import java.util.List;

public interface ArticleRankingService {
    List<ArticleVO> top(Integer limit);
}
