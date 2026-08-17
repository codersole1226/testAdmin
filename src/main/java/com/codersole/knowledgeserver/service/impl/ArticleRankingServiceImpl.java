package com.codersole.knowledgeserver.service.impl;

import com.codersole.knowledgeserver.mapper.ArticleMapper;
import com.codersole.knowledgeserver.service.ArticleRankingService;
import com.codersole.knowledgeserver.vo.ArticleVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleRankingServiceImpl implements ArticleRankingService {
    private final StringRedisTemplate stringRedisTemplate;
    private final ArticleMapper articleMapper;

    public ArticleRankingServiceImpl(StringRedisTemplate stringRedisTemplate, ArticleMapper articleMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.articleMapper = articleMapper;
    }

    @Override
    public List<ArticleVO> top(Integer limit) {
        Set<String> ids = stringRedisTemplate.opsForZSet().reverseRange("article:hot", 0, limit - 1);

        if(ids == null || ids.isEmpty()) {
            return List.of();
        }
        List<Long> articleIds = ids.stream().map(Long::valueOf).toList();

        List<ArticleVO> articles= articleMapper.selectVOByIds(articleIds);
        Map<Long, Integer> rankMap = new HashMap<>();
        for (int i = 0; i < articleIds.size(); i++) {
            rankMap.put(articleIds.get(i), i);
        }
        articles.sort(Comparator.comparingInt(article -> rankMap.get(article.getId())));
        return articles;
    }
}
