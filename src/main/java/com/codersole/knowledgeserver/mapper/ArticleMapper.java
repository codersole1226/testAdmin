package com.codersole.knowledgeserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codersole.knowledgeserver.dto.ArticleQueryDTO;
import com.codersole.knowledgeserver.entity.Article;
import com.codersole.knowledgeserver.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
//    @Select("""
//
//            SELECT
//
//                a.id,
//
//                a.title,
//
//                a.content,
//
//                a.category_id,
//
//                a.create_time,
//
//                c.name AS category_name
//
//            FROM article a
//
//            LEFT JOIN category c
//
//                ON a.category_id = c.id
//
//            WHERE a.id = #{id}
//
//            """)
    ArticleVO selectVOById(@Param("id") Long id);

    IPage<ArticleVO> selectVOPage(Page<ArticleVO> page, @Param("query")ArticleQueryDTO query);
}
