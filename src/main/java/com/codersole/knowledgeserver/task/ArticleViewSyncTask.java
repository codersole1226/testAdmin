package com.codersole.knowledgeserver.task;

import com.codersole.knowledgeserver.mapper.ArticleMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ArticleViewSyncTask {
    private final StringRedisTemplate stringRedisTemplate;
    private final ArticleMapper articleMapper;


    private static final DefaultRedisScript<Long> GET_AND_RESET_SCRIPT;

    static {
        GET_AND_RESET_SCRIPT = new DefaultRedisScript<>();
        GET_AND_RESET_SCRIPT.setScriptText("""
            local value = redis.call('GET', KEYS[1])
            if value then
                redis.call('SET', KEYS[1], 0)
                return tonumber(value)
            end
            return 0
            """);
        GET_AND_RESET_SCRIPT.setResultType(Long.class);
    }

    public ArticleViewSyncTask(StringRedisTemplate stringRedisTemplate, ArticleMapper articleMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.articleMapper = articleMapper;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void syncArticleViews() {
        Set<String> keys = stringRedisTemplate.keys("article:view:*");

        if(keys == null || keys.isEmpty()) {
            return;
        }
        for (String key : keys) {
            Long increment =
                    stringRedisTemplate.execute(
                            GET_AND_RESET_SCRIPT,
                            List.of(key)
                    );
            if (increment == null || increment <= 0) {
                continue;
            }
            Long articleId =
                    Long.valueOf(
                            key.substring(
                                    "article:view:".length()
                            )
                    );
            articleMapper.incrementViewCount(
                    articleId,
                    increment
            );
        }
    }
}
