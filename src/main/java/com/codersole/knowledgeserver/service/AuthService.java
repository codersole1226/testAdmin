package com.codersole.knowledgeserver.service;

import com.codersole.knowledgeserver.dto.LoginDTO;
import com.codersole.knowledgeserver.vo.LoginVO;
import com.codersole.knowledgeserver.vo.UserVO;

public interface AuthService {
    LoginVO login(LoginDTO dto);
}
