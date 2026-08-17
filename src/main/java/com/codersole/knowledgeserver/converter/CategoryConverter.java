package com.codersole.knowledgeserver.converter;

import com.codersole.knowledgeserver.dto.CategoryCreateDTO;
import com.codersole.knowledgeserver.dto.CategoryUpdateDTO;
import com.codersole.knowledgeserver.entity.Category;
import com.codersole.knowledgeserver.vo.CategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryConverter {

    Category toEntity(CategoryCreateDTO dto);
    CategoryVO toVo(Category category);

    List<CategoryVO> toVoList(List<Category> categories);

    void updateEntity(CategoryUpdateDTO dto, @MappingTarget Category category);

}
