package com.codersole.knowledgeserver.security;

import com.codersole.knowledgeserver.context.UserContext;
import com.codersole.knowledgeserver.enums.UserRole;
import com.codersole.knowledgeserver.exception.BusinessException;

import java.util.List;

public class PermissionUtils {
    public static boolean isAdmin() {
        return UserRole.ADMIN.name().equals(UserContext.getRole());
    }

    public static void checkOwnerOrAdmin(Long ownerId) {
        Long currentUserId = UserContext.getUserId();
        if (!isAdmin() && !ownerId.equals(currentUserId)) {
            throw new BusinessException(403, "无权限操作该资源");
        }
    }

    public static boolean hasPermission(String permission) {
        List<String> allPermissions = UserContext.getPermissions();
        return permission != null && allPermissions.contains(permission);
    }

    public static void checkPermission(String permission) {
        if(!hasPermission(permission)) {
            throw new BusinessException(403, "无权限操作");
        }
    }

    public static void checkOwnerAny(
            Long ownerId,
            String anyPermission
    ) {
        Long currentUserId = UserContext.getUserId();
        boolean isOwner = ownerId.equals(currentUserId);
        boolean hasAnyPermission = hasPermission(anyPermission);

        if(!isOwner && !hasAnyPermission) {
            throw new BusinessException(403, "无权限操作该数据");
        }
    }
}
