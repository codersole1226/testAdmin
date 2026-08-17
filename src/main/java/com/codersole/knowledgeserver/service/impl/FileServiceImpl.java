package com.codersole.knowledgeserver.service.impl;

import com.codersole.knowledgeserver.context.UserContext;
import com.codersole.knowledgeserver.entity.FileInfo;
import com.codersole.knowledgeserver.exception.BusinessException;
import com.codersole.knowledgeserver.mapper.FileInfoMapper;
import com.codersole.knowledgeserver.service.FileService;
import com.codersole.knowledgeserver.vo.FileVO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final FileInfoMapper fileInfoMapper;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp", "pdf", "doc", "docx");
    private static final Set<String> ALLOWED_CONTENT_TYPES =
        Set.of("image/jpeg", "image/png", "image/webp", "application/pdf", "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    private static final long MAX_SIZE = 10 * 1024 * 1024;
    private static final Path UPLOAD_PATH = Paths.get("uploads").toAbsolutePath().normalize();

    public FileServiceImpl(FileInfoMapper fileInfoMapper) {
        this.fileInfoMapper = fileInfoMapper;
    }


    @Override
    public FileVO upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(400, "文件不能为空");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new BusinessException(400, "文件大小不能超过10MB");
        }

        String originalFilename = file.getOriginalFilename();
        String suffix = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        }

        if (!ALLOWED_EXTENSIONS.contains(suffix)) {
            throw new BusinessException(400, "不支持的文件类型");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new BusinessException(400, "不支持的文件类型");
        }
        String filename = UUID.randomUUID() + "." + suffix;


        String storedName =

                UUID.randomUUID() + "." + suffix;

        Path uploadDir =

                Paths.get("uploads")

                        .toAbsolutePath()

                        .normalize();

        try {

            Files.createDirectories(uploadDir);

            Path filePath =

                    uploadDir.resolve(storedName);

            file.transferTo(filePath);

        } catch (IOException e) {

            throw new BusinessException(

                    500,

                    "文件上传失败"

            );

        }

        String url =

                "/uploads/" + storedName;

        FileInfo fileInfo = new FileInfo();

        fileInfo.setOriginalName(originalFilename);

        fileInfo.setStoredName(storedName);

        fileInfo.setFilePath(url);

        fileInfo.setContentType(file.getContentType());

        fileInfo.setFileSize(file.getSize());

        fileInfo.setUserId(UserContext.getUserId());

        fileInfo.setCreateTime(fileInfo.getCreateTime());

        fileInfoMapper.insert(fileInfo);

        // 暂时手动转换

        FileVO vo = new FileVO();

        vo.setId(fileInfo.getId());

        vo.setOriginalName(fileInfo.getOriginalName());

        vo.setUrl(fileInfo.getFilePath());

        vo.setFileSize(fileInfo.getFileSize());

        vo.setContentType(fileInfo.getContentType());

        return vo;
    }

    @Override
    public ResponseEntity<Resource> download(String filename) {
        Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
        Path filePath = uploadDir.resolve(filename).normalize();
        if (!filePath.startsWith(uploadDir)) {
            throw new BusinessException(400, "非法文件路径");
        }
        if (!Files.exists(filePath)) {
            throw new BusinessException(404, "文件不存在");
        }
        try {
            Resource resource = new UrlResource(filePath.toUri());
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        } catch (MalformedURLException e) {
            throw new BusinessException(500, "文件读取失败");
        }
    }
}
