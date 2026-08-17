package com.codersole.knowledgeserver.service.impl;

import com.codersole.knowledgeserver.context.UserContext;
import com.codersole.knowledgeserver.entity.Article;
import com.codersole.knowledgeserver.exception.BusinessException;
import com.codersole.knowledgeserver.mapper.ArticleMapper;
import com.codersole.knowledgeserver.service.ArticleLikeService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeServiceImpl implements ArticleLikeService {
    private final StringRedisTemplate stringRedisTemplate;
    private final ArticleMapper articleMapper;

    public ArticleLikeServiceImpl(StringRedisTemplate stringRedisTemplate, ArticleMapper articleMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.articleMapper = articleMapper;
    }
    private String buildKey(Long articleId) {
        return "article:likes:" + articleId;
    }

    @Override
    public void like(Long articleId) {
        Long userId = UserContext.getUserId();
        Article article = articleMapper.selectById(articleId);
        if(article == null) {
            throw new BusinessException(404, "文章不存在");
        }
        Long added = stringRedisTemplate.opsForSet().add(buildKey(articleId), String.valueOf(userId));

        if(Long.valueOf(1L).equals(added)) {
            stringRedisTemplate.opsForZSet().incrementScore("article:hot", String.valueOf(articleId),1);
        }

    }

    @Override
    public void unlike(Long articleId) {
        Long userId = UserContext.getUserId();
        Long removed = stringRedisTemplate.opsForSet().remove(buildKey(articleId),String.valueOf(userId));
        if(Long.valueOf(1L).equals(removed)) {
            stringRedisTemplate.opsForZSet().incrementScore("article:hot", String.valueOf(articleId), -1);
        }
    }

    @Override
    public boolean isLiked(Long articleId) {
        Long userId = UserContext.getUserId();
        Boolean result = stringRedisTemplate.opsForSet().isMember(buildKey(articleId), String.valueOf(userId));
        return Boolean.TRUE.equals(result);
    }

    @Override
    public Long count(Long articled) {
        Long size = stringRedisTemplate.opsForZSet().size(buildKey(articled));

        return size == null ? 0L : size;
    }
}
