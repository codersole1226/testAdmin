package com.codersole.knowledgeserver.interceptor;

import com.codersole.knowledgeserver.context.UserContext;
import com.codersole.knowledgeserver.exception.BusinessException;
import com.codersole.knowledgeserver.mapper.PermissionMapper;
import com.codersole.knowledgeserver.security.PermissionUtils;
import com.codersole.knowledgeserver.service.PermissionService;
import com.codersole.knowledgeserver.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtUtils jwtUtils;
    private final PermissionService permissionService;

    public AuthInterceptor(JwtUtils jwtUtils, PermissionService permissionService) {
        this.jwtUtils = jwtUtils;
        this.permissionService = permissionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new BusinessException(
                401,
                "未登录或登录已失效"
            );
        }

        String token = authorization.substring(7);
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(
                401,
                "Token无效或已过期"
            );
        }
        Long userId = jwtUtils.getUserId(token);
        String role = jwtUtils.getRole(token);
//        List<String> permissions = permissionMapper.selectCodesByUserId(userId);
        List<String> permissions = permissionService.getUserPermissions(userId);

        UserContext.setUserId(userId);
        UserContext.setRole(role);
        UserContext.setPermissions(permissions);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler,Exception ex) {
        UserContext.clear();
    }
}
