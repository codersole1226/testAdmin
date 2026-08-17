package com.codersole.knowledgeserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codersole.knowledgeserver.common.Result;
import com.codersole.knowledgeserver.converter.CategoryConverter;
import com.codersole.knowledgeserver.converter.UserConverter;
import com.codersole.knowledgeserver.dto.CategoryCreateDTO;
import com.codersole.knowledgeserver.dto.CategoryUpdateDTO;
import com.codersole.knowledgeserver.entity.Category;
import com.codersole.knowledgeserver.exception.BusinessException;
import com.codersole.knowledgeserver.mapper.CategoryMapper;
import com.codersole.knowledgeserver.service.CategoryService;
import com.codersole.knowledgeserver.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryConverter categoryConverter;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryConverter categoryConverter) {
        this.categoryMapper = categoryMapper;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public CategoryVO create(CategoryCreateDTO dto) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getName, dto.getName());

        Category existingCategory = categoryMapper.selectOne(wrapper);

        if(existingCategory != null) {
            throw new BusinessException(400, "分类名已存在");
        }

        Category category = categoryConverter.toEntity(dto);

        categoryMapper.insert(category);

        // 为了能拿到自动生成的时间
        Category savedCategory = categoryMapper.selectById(category.getId());

        return categoryConverter.toVo(savedCategory);
    }

    @Override
    public CategoryVO getById(Long id) {
        Category category = categoryMapper.selectById(id);
        if(category == null) {
            throw new BusinessException(404,"分类不存在");
        }
        return categoryConverter.toVo(category);
    }

    @Override
    public List<CategoryVO> list() {
        List<Category> categories = categoryMapper.selectList(null);

        return categoryConverter.toVoList(categories);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryMapper.selectById(id);

        if(category == null) {
            throw new BusinessException(404, "分类不存在");
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public CategoryVO update(Long id, CategoryUpdateDTO dto) {
        Category category = categoryMapper.selectById(id);
        if(category ==null) {
            throw new BusinessException(404, "分类不存在");
        }
        categoryConverter.updateEntity(dto, category);

        categoryMapper.updateById(category);

        return categoryConverter.toVo(category);
    }
}
