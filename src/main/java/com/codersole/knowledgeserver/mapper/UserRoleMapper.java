package com.codersole.knowledgeserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper{
    void deleteByUserId(@Param("userId") Long userId);

    void insertRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    List<Long> selectUserIdsByRoleId(@Param("roleId") Long roleId);
}
