package com.codersole.knowledgeserver.service.impl;

import com.codersole.knowledgeserver.dto.RolePermissionAssignDTO;
import com.codersole.knowledgeserver.entity.Permission;
import com.codersole.knowledgeserver.entity.Role;
import com.codersole.knowledgeserver.exception.BusinessException;
import com.codersole.knowledgeserver.mapper.PermissionMapper;
import com.codersole.knowledgeserver.mapper.RoleMapper;
import com.codersole.knowledgeserver.mapper.RolePermissionMapper;
import com.codersole.knowledgeserver.mapper.UserRoleMapper;
import com.codersole.knowledgeserver.service.PermissionService;
import com.codersole.knowledgeserver.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;

    private final PermissionMapper permissionMapper;

    private final RolePermissionMapper rolePermissionMapper;

    private final UserRoleMapper userRoleMapper;

    private final PermissionService permissionService;

    public RoleServiceImpl(RoleMapper roleMapper, PermissionMapper permissionMapper, RolePermissionMapper rolePermissionMapper, UserRoleMapper userRoleMapper, PermissionService permissionService) {
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.userRoleMapper = userRoleMapper;
        this.permissionService = permissionService;
    }

    @Override
    @Transactional
    public void assignPermissions(Long roleId, RolePermissionAssignDTO dto) {
        Role role = roleMapper.selectById(roleId);

        if(role == null ) {
            throw new BusinessException(

                    404,

                    "角色不存在"

            );
        }

        Set<Long> permissionIds = new HashSet<>(dto.getPermissionIds());
        List<Permission> permissions = permissionMapper.selectByIds(permissionIds);

        if(permissions.size() != permissionIds.size()) {
            throw new BusinessException(

                    400,

                    "存在无效的权限"

            );
        }

        List<Long> userIds = userRoleMapper.selectUserIdsByRoleId(roleId);

        rolePermissionMapper.deleteByRoleId(roleId);

        for (Long permissionId : permissionIds) {
            rolePermissionMapper.insertPermission(roleId, permissionId);
        }

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        permissionService.clearUserPermissionCaches(userIds);
//                        for (Long userId : userIds) {
//                            permissionService.clearUserPermissionCache(userId);
//                        }
                    }
                }
        );
    }
}
