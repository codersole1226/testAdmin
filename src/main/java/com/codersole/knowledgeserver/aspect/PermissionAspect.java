package com.codersole.knowledgeserver.aspect;

import com.codersole.knowledgeserver.annotation.RequirePermission;
import com.codersole.knowledgeserver.security.PermissionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {
    @Around("@annotation(requirePermission)")
    public Object checkPermission(
            ProceedingJoinPoint joinPoint,
            RequirePermission requirePermission
    ) throws Throwable{
        String permission = requirePermission.value();
        PermissionUtils.checkPermission(permission);
        return joinPoint.proceed();
    }
}
