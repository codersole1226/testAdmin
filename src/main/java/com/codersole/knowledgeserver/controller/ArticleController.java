package com.codersole.knowledgeserver.controller;

import com.codersole.knowledgeserver.common.Result;
import com.codersole.knowledgeserver.dto.ArticleCreateDTO;
import com.codersole.knowledgeserver.dto.ArticleQueryDTO;
import com.codersole.knowledgeserver.dto.ArticleUpdateDTO;
import com.codersole.knowledgeserver.service.ArticleService;
import com.codersole.knowledgeserver.vo.ArticleVO;
import com.codersole.knowledgeserver.vo.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@Tag(name = "文章管理")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    @Operation(summary = "分页查询文章")
    public Result<PageVO<ArticleVO>> page(@Parameter(description = "文章分页查询参数") @Valid ArticleQueryDTO query) {
        return Result.success(
                articleService.page(query)
        );
    }

    @PostMapping
    @Operation(summary = "创建文章")
    public Result<ArticleVO> create(@Parameter(description = "文章创建信息") @Valid @RequestBody ArticleCreateDTO dto) {
        return Result.success(articleService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新文章")
    public Result<ArticleVO> update(
            @Parameter(description = "文章ID", example = "1") @PathVariable Long id,
            @Parameter(description = "文章更新信息") @Valid @RequestBody ArticleUpdateDTO dto
    ) {
        return Result.success(articleService.update(id,dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章")
    public Result<Void> delete(@Parameter(description = "文章ID", example = "1") @PathVariable Long id) {
        articleService.delete(id);
        return Result.success();
    }
}
