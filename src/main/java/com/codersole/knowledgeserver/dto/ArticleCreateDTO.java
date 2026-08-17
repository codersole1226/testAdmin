package com.codersole.knowledgeserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "ArticleCreateDTO", description = "创建文章请求")
public class ArticleCreateDTO {
    @Schema(description = "文章标题", example = "Spring Boot 入门", maxLength = 20)
    @NotBlank(message = "文章标题不能为空")
    @Size(max = 20, message = "文章标题不能超过20个字")
    private String title;

    @Schema(description = "文章内容", example = "这是一篇关于 Spring Boot 的文章")
    private String content;

    @Schema(description = "分类ID", example = "1")
    @NotNull(message = "文章分类必须存在")
    private Long categoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
