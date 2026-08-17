package com.codersole.knowledgeserver.controller;

import com.codersole.knowledgeserver.common.Result;
import com.codersole.knowledgeserver.dto.*;
import com.codersole.knowledgeserver.service.UserRoleService;
import com.codersole.knowledgeserver.service.UserService;
import com.codersole.knowledgeserver.vo.PageVO;
import com.codersole.knowledgeserver.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "用户管理")
public class UserController {
    private final UserService userService;
    private final UserRoleService userRoleService;

    public UserController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户")
    public Result<UserVO> getUser(@Parameter(description = "用户ID", example = "1") @PathVariable Long id) {
        return Result.success(userService.getById(id));
    }


    @GetMapping
    @Operation(summary = "分页查询用户")
    public Result<PageVO<UserVO>> listPage(@Parameter(description = "用户分页查询参数") @Valid UserQueryDTO query) {
        return Result.success(userService.page(query));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户")
    public Result<UserVO> update(
            @Parameter(description = "用户ID", example = "1") @PathVariable Long id,
            @Parameter(description = "用户更新信息") @Valid @RequestBody UserUpdateDTO dto
    ) {
        return Result.success(userService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public Result<Void> delete(@Parameter(description = "用户ID", example = "1") @PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result<UserVO> register(@Parameter(description = "用户注册信息") @Valid @RequestBody UserRegisterDTO dto) {
        return Result.success(userService.register(dto));
    }

    @PutMapping("/{id}/roles")
    @Operation(summary = "分配用户角色")
    public Result<Void> assignRoles(
            @Parameter(description = "用户ID", example = "1") @PathVariable Long id,
            @Parameter(description = "角色分配信息") @Valid @RequestBody UserRoleAssignDTO dto
    ) {
        userRoleService.assignRoles(id, dto);
        return Result.success();
    }
}
