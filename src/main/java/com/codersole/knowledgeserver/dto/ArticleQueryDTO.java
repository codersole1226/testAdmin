package com.codersole.knowledgeserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(name = "ArticleQueryDTO", description = "文章分页查询参数")
public class ArticleQueryDTO {
    @Schema(description = "页码", example = "1", defaultValue = "1")
    @Min(value = 1, message = "页码必须大于等于1")
    private Long page = 1L;

    @Schema(description = "每页条数", example = "10", defaultValue = "10")
    @Min(value = 1, message = "每页条数必须大于等于1")
    private Long pageSize = 10L;

    @Schema(description = "文章标题关键字", example = "Spring")
    private String title;

    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
