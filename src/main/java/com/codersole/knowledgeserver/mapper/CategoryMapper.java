package com.codersole.knowledgeserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codersole.knowledgeserver.entity.Category;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
