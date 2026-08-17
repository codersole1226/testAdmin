package com.codersole.knowledgeserver.service;

import com.codersole.knowledgeserver.dto.CategoryCreateDTO;
import com.codersole.knowledgeserver.dto.CategoryUpdateDTO;
import com.codersole.knowledgeserver.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    CategoryVO create(CategoryCreateDTO category);

    CategoryVO getById(Long id);

    List<CategoryVO> list();

    void delete(Long id);

    CategoryVO update(Long id, CategoryUpdateDTO category);
}
