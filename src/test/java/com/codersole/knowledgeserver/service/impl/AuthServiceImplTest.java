package com.codersole.knowledgeserver.service.impl;

import com.codersole.knowledgeserver.converter.UserConverter;
import com.codersole.knowledgeserver.dto.LoginDTO;
import com.codersole.knowledgeserver.entity.User;
import com.codersole.knowledgeserver.mapper.UserMapper;
import com.codersole.knowledgeserver.util.JwtUtils;
import com.codersole.knowledgeserver.vo.UserVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserConverter userConverter;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Mock
    private JwtUtils jwtUtils;

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authService = new AuthServiceImpl(
                userMapper,
                userConverter,
                passwordEncoder,
                jwtUtils
        );
    }

    @Test
    void testLoginSuccess() {

    }
}