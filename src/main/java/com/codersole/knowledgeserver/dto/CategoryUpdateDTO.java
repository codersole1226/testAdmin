package com.codersole.knowledgeserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "CategoryUpdateDTO", description = "更新分类请求")
public class CategoryUpdateDTO {
    @Schema(description = "分类名称", example = "后端", maxLength = 10)
    @NotBlank(message = "分类名不能为空")
    @Size(max = 10,message = "分类名长度不能超过10个字")
    private String name;

    @Schema(description = "分类描述", example = "后端开发相关内容", maxLength = 255)
    @Size(max = 255,message = "分类描述不能超过255个字")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
