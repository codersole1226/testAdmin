package com.codersole.knowledgeserver.controller;

import com.codersole.knowledgeserver.common.Result;
import com.codersole.knowledgeserver.dto.FileUploadDTO;
import com.codersole.knowledgeserver.service.FileService;
import com.codersole.knowledgeserver.vo.FileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
@Tag(name = "文件管理")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public Result<FileVO> upload(@Parameter(description = "文件上传信息") @Valid @ModelAttribute FileUploadDTO dto) {
        return Result.success(fileService.upload(dto.getFile()));
    }

    @GetMapping("/download/{filename}")
    @Operation(summary = "下载文件")
    public ResponseEntity<Resource> download(
        @Parameter(description = "文件名", example = "a3f2f6d4-6c5b-4c17-8d91-9f0d2a8e5c11.pdf") @PathVariable String filename
    ) {
        return fileService.download(filename);
    }
}
