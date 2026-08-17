package com.codersole.knowledgeserver.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class RolePermissionAssignDTO {

    @NotEmpty(message = "权限不能为空")
    private List<Long> permissionIds;

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
