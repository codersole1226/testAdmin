package com.codersole.knowledgeserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codersole.knowledgeserver.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
