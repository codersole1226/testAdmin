package com.codersole.knowledgeserver.service.impl;

import com.codersole.knowledgeserver.service.ArticleService;
import com.codersole.knowledgeserver.service.ArticleViewService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ArticleViewServiceImpl implements ArticleViewService {

    private final StringRedisTemplate stringRedisTemplate;

    public ArticleViewServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Long increment(Long articleId) {
        String key = "article:view:" + articleId;

        return stringRedisTemplate.opsForValue().increment(key);
    }

    @Override
    public Long getIncrement(Long articleId) {
        String key = "article:view:"  + articleId;
        String value = stringRedisTemplate.opsForValue().get(key);
        if(value == null) {
            return 0L;
        }
        return Long.parseLong(value);
    }
}
