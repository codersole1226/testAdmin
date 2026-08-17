package com.codersole.knowledgeserver.service.impl;

import com.codersole.knowledgeserver.dto.UserRoleAssignDTO;
import com.codersole.knowledgeserver.entity.Role;
import com.codersole.knowledgeserver.entity.User;
import com.codersole.knowledgeserver.exception.BusinessException;
import com.codersole.knowledgeserver.mapper.RoleMapper;
import com.codersole.knowledgeserver.mapper.UserMapper;
import com.codersole.knowledgeserver.mapper.UserRoleMapper;
import com.codersole.knowledgeserver.service.PermissionService;
import com.codersole.knowledgeserver.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final PermissionService permissionService;

    public UserRoleServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper, RoleMapper roleMapper, PermissionService permissionService) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
        this.permissionService = permissionService;
    }

    @Override
    @Transactional
    public void assignRoles(Long userId, UserRoleAssignDTO dto) {
        User user = userMapper.selectById(userId);
        if(user == null) {
            throw new BusinessException(404, "用户不存在");
        }

        Set<Long> roleIds = new HashSet<>(dto.getRoleIds());
        List<Role> roles = roleMapper.selectByIds(roleIds);

        if(roles.size() != roleIds.size()) {
            throw new BusinessException(404, "存在无效角色");
        }
        userRoleMapper.deleteByUserId(userId);
        for (Long roleId : roleIds) {
            userRoleMapper.insertRole(userId,roleId);
        }

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        permissionService.clearUserPermissionCache(userId);
                    }
                }
        );
    }
}
