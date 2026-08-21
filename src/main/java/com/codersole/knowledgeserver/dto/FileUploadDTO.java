package com.codersole.knowledgeserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Schema(name = "FileUploadDTO", description = "文件上传请求")
public class FileUploadDTO {
    @Schema(description = "上传文件", type = "string", format = "binary")
    @NotNull(message = "上传文件不能为空")
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
