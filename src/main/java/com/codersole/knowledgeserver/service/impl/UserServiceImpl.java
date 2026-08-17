package com.codersole.knowledgeserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codersole.knowledgeserver.converter.UserConverter;
import com.codersole.knowledgeserver.dto.UserCreateDTO;
import com.codersole.knowledgeserver.dto.UserQueryDTO;
import com.codersole.knowledgeserver.dto.UserRegisterDTO;
import com.codersole.knowledgeserver.dto.UserUpdateDTO;
import com.codersole.knowledgeserver.entity.User;
import com.codersole.knowledgeserver.exception.BusinessException;
import com.codersole.knowledgeserver.mapper.UserMapper;
import com.codersole.knowledgeserver.service.UserService;
import com.codersole.knowledgeserver.vo.PageVO;
import com.codersole.knowledgeserver.vo.UserVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserConverter userConverter;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, UserConverter userConverter, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserVO getById(Long id) {

        User user = userMapper.selectById(id);

        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }

        // UserVO vo = new UserVO();
        //
        // vo.setId(user.getId());
        // vo.setAge(user.getAge());
        // vo.setName(user.getName());
        //
        // return vo;

        return userConverter.toVo(user);

    }

    @Override
    public List<UserVO> list() {
        List<User> users = userMapper.selectList(null);

        return userConverter.toVOList(users);

        // List<UserVO> result = new ArrayList<>();
        //
        // for (User user : users) {
        // UserVO vo = new UserVO();
        //
        // vo.setId(user.getId());
        // vo.setName(user.getName());
        // vo.setAge(user.getAge());
        //
        // result.add(vo);
        // }
        // return result;
    }

    @Override
    public UserVO create(UserCreateDTO dto) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName, dto.getName());

        User existingUser = userMapper.selectOne(wrapper);

        if (existingUser != null) {
            throw new BusinessException(400, "用户名已经存在");
        }

        // User user = new User();
        // user.setName(dto.getName());
        // user.setAge(dto.getAge());
        User user = userConverter.toEntity(dto);

        userMapper.insert(user);
        return userConverter.toVo(user);
        // return user;
    }

    @Override
    public UserVO update(UserUpdateDTO dto, Long id) {
        User user = userMapper.selectById(id);

        if (user == null) {
            throw new BusinessException(400, "用户不存在");
        }

        userConverter.updateEntity(dto, user);

        // user.setAge(dto.getAge());
        // user.setName(dto.getName());

        userMapper.updateById(user);
        // return user;
        return userConverter.toVo(user);
    }

    @Override
    public void delete(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        userMapper.deleteById(id);
    }

    @Override
    public PageVO<UserVO> page(UserQueryDTO query) {
        Page<User> page = new Page<>(query.getPage(), query.getPageSize());

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        if(query.getName() != null && !query.getName().isBlank()) {
            wrapper.like(User::getName, query.getName());
        }

        if(query.getMinAge() != null && query.getMinAge() > 0) {
            wrapper.ge(User::getAge, query.getMinAge());
        }

        if(query.getMaxAge() != null && query.getMaxAge() <= 150) {
            wrapper.le(User::getAge, query.getMaxAge());
        }

        if ("age".equals(query.getSortBy())) {

            if ("desc".equalsIgnoreCase(query.getOrder())) {

                wrapper.orderByDesc(User::getAge);

            } else {

                wrapper.orderByAsc(User::getAge);

            }

        } else if ("name".equals(query.getSortBy())) {

            if ("desc".equalsIgnoreCase(query.getOrder())) {

                wrapper.orderByDesc(User::getName);

            } else {

                wrapper.orderByAsc(User::getName);

            }

        } else if ("id".equals(query.getSortBy())) {

            if ("desc".equalsIgnoreCase(query.getOrder())) {

                wrapper.orderByDesc(User::getId);

            } else {

                wrapper.orderByAsc(User::getId);

            }

        }
        userMapper.selectPage(page, wrapper);

        // 转成给前端的PageVo

        PageVO<UserVO> result = new PageVO<>();

        result.setTotal(page.getTotal());

        result.setPage(page.getCurrent());

        result.setPageSize(page.getSize());

        result.setRecords(

            userConverter.toVOList(page.getRecords())

        );

        return result;
    }

    @Override
    public UserVO register(UserRegisterDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(User::getUsername, dto.getUsername());

        User existingUser = userMapper.selectOne(wrapper);

        if(existingUser != null) {
            throw new BusinessException(400,"用户名偶存在");
        }

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setAge(dto.getAge());

        String encodePassword = passwordEncoder.encode(dto.getPassword());

        user.setPassword(encodePassword);
        userMapper.insert(user);
        return userConverter.toVo(user);
    }
}
