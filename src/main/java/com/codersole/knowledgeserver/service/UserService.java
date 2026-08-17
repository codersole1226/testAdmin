package com.codersole.knowledgeserver.service;

import com.codersole.knowledgeserver.dto.UserCreateDTO;
import com.codersole.knowledgeserver.dto.UserQueryDTO;
import com.codersole.knowledgeserver.dto.UserRegisterDTO;
import com.codersole.knowledgeserver.dto.UserUpdateDTO;
import com.codersole.knowledgeserver.vo.PageVO;
import com.codersole.knowledgeserver.vo.UserVO;

import java.util.List;

public interface UserService {
    UserVO getById(Long id);

    List<UserVO> list();

    UserVO create(UserCreateDTO user);

    UserVO update(UserUpdateDTO user, Long id);

    void delete(Long id);


    PageVO<UserVO> page(UserQueryDTO query);

    UserVO register(UserRegisterDTO dto);
}