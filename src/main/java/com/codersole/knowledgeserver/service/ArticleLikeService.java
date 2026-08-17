package com.codersole.knowledgeserver.service;

public interface ArticleLikeService {
    void like(Long articleId);
    void unlike(Long articleId);

    boolean isLiked(Long articleId);

    Long count(Long articled);
}
