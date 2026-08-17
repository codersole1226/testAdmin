package com.codersole.knowledgeserver.service.impl;

import com.codersole.knowledgeserver.mapper.PermissionMapper;
import com.codersole.knowledgeserver.service.PermissionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionMapper permissionMapper;

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper, StringRedisTemplate stringRedisTemplate,
        ObjectMapper objectMapper) {
        this.permissionMapper = permissionMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<String> getUserPermissions(Long userId) {
        String key = "user:permission:" + userId;

        String cache = stringRedisTemplate.opsForValue().get(key);
        try {
            if (cache != null) {
                try {
                    return objectMapper.readValue(cache, new TypeReference<List<String>>() {});
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            List<String> permissions = permissionMapper.selectCodesByUserId(userId);

            String json = objectMapper.writeValueAsString(permissions);

            stringRedisTemplate.opsForValue().set(key, json, 30, TimeUnit.MINUTES);
            return permissions;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("权限缓存处理失败", e);
        }

    }

    @Override
    public void clearUserPermissionCache(Long userId) {
        String key = "user:permission:" + userId;
        stringRedisTemplate.delete(key);
    }

    @Override
    public void clearUserPermissionCaches(List<Long> userIds) {
        if(userIds == null || userIds.isEmpty()) {
            return;
        }

        List<String> keys = userIds.stream().map(id -> "user:permission:" + id).toList();

        stringRedisTemplate.delete(keys);
    }
}
