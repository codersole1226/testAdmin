package com.codersole.knowledgeserver.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(name = "UserRoleAssignDTO", description = "用户角色分配请求")
public class UserRoleAssignDTO {
    @ArraySchema(schema = @Schema(description = "角色ID", example = "1"), arraySchema = @Schema(description = "角色ID列表"))
    @NotEmpty(message = "角色不能为空")
    private List<Long> roleIds;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
