package com.codersole.knowledgeserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codersole.knowledgeserver.converter.UserConverter;
import com.codersole.knowledgeserver.dto.LoginDTO;
import com.codersole.knowledgeserver.entity.User;
import com.codersole.knowledgeserver.exception.BusinessException;
import com.codersole.knowledgeserver.mapper.UserMapper;
import com.codersole.knowledgeserver.service.AuthService;
import com.codersole.knowledgeserver.util.JwtUtils;
import com.codersole.knowledgeserver.vo.LoginVO;
import com.codersole.knowledgeserver.vo.UserVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserMapper userMapper;
    private final UserConverter userConverter;


    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserMapper userMapper, UserConverter userConverter, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginVO login(LoginDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(User::getUsername, dto.getUsername());
        User existingUser = userMapper.selectOne(wrapper);
        if (existingUser == null) {
            throw new BusinessException(400, "用户名或者密码错误");
        }

        boolean isMatch = passwordEncoder.matches(dto.getPassword(), existingUser.getPassword());

        if (!isMatch) {
            throw new BusinessException(400, "用户名或者密码错误");
        }

        LoginVO loginVO = new LoginVO();
        String token = jwtUtils.generateToken(existingUser.getId(),existingUser.getUsername(), existingUser.getRole());

        loginVO.setToken(token);
        loginVO.setUser(userConverter.toVo(existingUser));

        return loginVO;
    }
}
