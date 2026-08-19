package com.codersole.knowledgeserver.service.impl;

import com.codersole.knowledgeserver.service.CodeService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class CodeServiceImpl implements CodeService {
    private final StringRedisTemplate stringRedisTemplate;

    private static final DefaultRedisScript<Long> VERIFY_CODE_SCRIPT;

    static {
        VERIFY_CODE_SCRIPT = new DefaultRedisScript<>();
        VERIFY_CODE_SCRIPT.setScriptText("""
                
                local value = redis.call('GET', KEYS[1])
                
                if not value then
                    return 0
                end
                
                if value == ARGV[1] then
                    redis.call('DEL', KEYS[1])
                    return 1
                end
                
                return 0
                """);
        VERIFY_CODE_SCRIPT.setResultType(Long.class);
    }

    public CodeServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String generate(String username) {
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        String key = "login:code:" + username;
        stringRedisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
        return code;
    }

    @Override
    public boolean verify(String username, String code) {
        String key = "login:code:" + username;
        Long result = stringRedisTemplate.execute(VERIFY_CODE_SCRIPT, List.of(key), code);
//        String redisCode = stringRedisTemplate.opsForValue().get(key);
//
//        if (redisCode == null) {
//            return false;
//        }
//
//        if (!redisCode.equals(code)) {
//            return false;
//        }
//        stringRedisTemplate.delete(key);
//
//        return true;
        return Long.valueOf(1L).equals(result);
    }
}
