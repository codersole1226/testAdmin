package com.codersole.knowledgeserver.controller;

import com.codersole.knowledgeserver.common.Result;
import com.codersole.knowledgeserver.dto.ArticleCreateDTO;
import com.codersole.knowledgeserver.dto.ArticleQueryDTO;
import com.codersole.knowledgeserver.dto.ArticleUpdateDTO;
import com.codersole.knowledgeserver.service.ArticleLikeService;
import com.codersole.knowledgeserver.service.ArticleRankingService;
import com.codersole.knowledgeserver.service.ArticleService;
import com.codersole.knowledgeserver.vo.ArticleVO;
import com.codersole.knowledgeserver.vo.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@Tag(name = "文章管理")
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleLikeService articleLikeService;
    private final ArticleRankingService articleRankingService;

    public ArticleController(ArticleService articleService, ArticleLikeService articleLikeService, ArticleRankingService articleRankingService) {
        this.articleService = articleService;
        this.articleLikeService = articleLikeService;
        this.articleRankingService = articleRankingService;
    }

    @GetMapping
    @Operation(summary = "分页查询文章")
    public Result<PageVO<ArticleVO>> page(@Parameter(description = "文章分页查询参数") @Valid ArticleQueryDTO query) {
        return Result.success(articleService.page(query));
    }

    @PostMapping
    @Operation(summary = "创建文章")
    public Result<ArticleVO> create(@Parameter(description = "文章创建信息") @Valid @RequestBody ArticleCreateDTO dto) {
        return Result.success(articleService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新文章")
    public Result<ArticleVO> update(@Parameter(description = "文章ID", example = "1") @PathVariable Long id,
        @Parameter(description = "文章更新信息") @Valid @RequestBody ArticleUpdateDTO dto) {
        return Result.success(articleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章")
    public Result<Void> delete(@Parameter(description = "文章ID", example = "1") @PathVariable Long id) {
        articleService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询文章")
    public Result<ArticleVO> getById(@Parameter(description = "文章ID", example = "1") @PathVariable Long id) {
        return Result.success(articleService.getById(id));
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "点赞文章")
    public Result<Void> like(@Parameter(description = "文章ID", example = "1") @PathVariable Long id) {
        articleLikeService.like(id);
        return Result.success();
    }

    @DeleteMapping("/{id}/like")
    @Operation(summary = "取消点赞")
    public Result<Void> unlike(@Parameter(description = "文章ID", example = "1") @PathVariable Long id) {
        articleLikeService.unlike(id);
        return Result.success();
    }

    @GetMapping("/{id}/like")
    @Operation(summary = "查询当前用户是否已点赞")
    public Result<Boolean> isLiked(@Parameter(description = "文章ID", example = "1") @PathVariable Long id) {
        return Result.success(articleLikeService.isLiked(id));
    }

    @GetMapping("/{id}/likes/count")
    @Operation(summary = "查询文章点赞数")
    public Result<Long> likeCount(@Parameter(description = "文章ID", example = "1") @PathVariable Long id) {
        return Result.success(articleLikeService.count(id));
    }

    @GetMapping("/hot")
    @Operation(summary = "查询热门文章")
    public Result<List<ArticleVO>> hot(
        @Parameter(description = "返回数量限制", example = "10") @RequestParam(defaultValue = "10") Integer limit
    ) {
        return Result.success(articleRankingService.top(limit));
    }
}
