package com.codersole.knowledgeserver.service;

import com.codersole.knowledgeserver.dto.RolePermissionAssignDTO;

public interface RoleService {
    void assignPermissions(

            Long roleId,

            RolePermissionAssignDTO dto

    );
}
