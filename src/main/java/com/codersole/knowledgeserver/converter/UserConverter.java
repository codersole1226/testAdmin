package com.codersole.knowledgeserver.converter;


import ch.qos.logback.core.model.ComponentModel;
import com.codersole.knowledgeserver.dto.UserCreateDTO;
import com.codersole.knowledgeserver.dto.UserUpdateDTO;
import com.codersole.knowledgeserver.entity.User;
import com.codersole.knowledgeserver.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter {

    User toEntity(UserCreateDTO dto);
    UserVO toVo(User user);

    List<UserVO> toVOList(List<User> users);

    void updateEntity(UserUpdateDTO dto, @MappingTarget User user);
}
