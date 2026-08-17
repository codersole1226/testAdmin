package com.codersole.knowledgeserver.service;

import com.codersole.knowledgeserver.vo.FileVO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileVO upload(MultipartFile file);

    ResponseEntity<Resource> download(String filename);
}
