package com.codersole.knowledgeserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codersole.knowledgeserver.entity.Permission;
import jakarta.validation.constraints.Max;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    List<String> selectCodesByUserId(@Param("userId") Long userId);
}
