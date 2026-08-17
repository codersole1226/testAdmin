package com.codersole.knowledgeserver.service;

import com.codersole.knowledgeserver.dto.UserRoleAssignDTO;

public interface UserRoleService {
    void assignRoles(Long userId, UserRoleAssignDTO dto);
}
