package com.codersole.knowledgeserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RolePermissionMapper {
    void deleteByRoleId(@Param("roleId") Long roleId);

    void insertPermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
