package com.codersole.knowledgeserver.controller;


import com.codersole.knowledgeserver.common.Result;
import com.codersole.knowledgeserver.dto.LoginDTO;
import com.codersole.knowledgeserver.service.AuthService;
import com.codersole.knowledgeserver.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "认证管理")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<LoginVO> validPassword(@Parameter(description = "登录信息") @Valid @RequestBody LoginDTO dto) {
        return Result.success(authService.login(dto));
    }
}
