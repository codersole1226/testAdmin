package com.codersole.knowledgeserver.service;

import java.util.List;

public interface PermissionService {
    List<String> getUserPermissions(Long userId);
    void clearUserPermissionCache(Long userId);

    void clearUserPermissionCaches(List<Long> userIds);
}
