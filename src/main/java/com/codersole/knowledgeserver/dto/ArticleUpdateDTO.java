package com.codersole.knowledgeserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "ArticleUpdateDTO", description = "更新文章请求")
public class ArticleUpdateDTO {
    @Schema(description = "文章标题", example = "Spring Boot 进阶", maxLength = 20)
    @Size(max = 20, message = "文章标题不能超过20个字")
    private String title;

    @Schema(description = "文章内容", example = "更新后的文章内容")
    private String content;

    @Schema(description = "分类ID", example = "2")
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
