package com.codersole.knowledgeserver.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codersole.knowledgeserver.annotation.RequirePermission;
import com.codersole.knowledgeserver.context.UserContext;
import com.codersole.knowledgeserver.converter.ArticleConverter;
import com.codersole.knowledgeserver.dto.ArticleCreateDTO;
import com.codersole.knowledgeserver.dto.ArticleQueryDTO;
import com.codersole.knowledgeserver.dto.ArticleUpdateDTO;
import com.codersole.knowledgeserver.entity.Article;
import com.codersole.knowledgeserver.entity.Category;
import com.codersole.knowledgeserver.exception.BusinessException;
import com.codersole.knowledgeserver.mapper.ArticleMapper;
import com.codersole.knowledgeserver.mapper.CategoryMapper;
import com.codersole.knowledgeserver.mapper.PermissionMapper;
import com.codersole.knowledgeserver.security.PermissionUtils;
import com.codersole.knowledgeserver.service.ArticleService;
import com.codersole.knowledgeserver.service.ArticleViewService;
import com.codersole.knowledgeserver.vo.ArticleVO;
import com.codersole.knowledgeserver.vo.PageVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;
    private final ArticleConverter articleConverter;
    private final PermissionMapper permissionMapper;
    private final ArticleViewService articleViewService;

    public ArticleServiceImpl(ArticleMapper articleMapper, CategoryMapper categoryMapper,
                              ArticleConverter articleConverter, PermissionMapper permissionMapper, ArticleViewService articleViewService) {
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
        this.articleConverter = articleConverter;
        this.permissionMapper = permissionMapper;
        this.articleViewService = articleViewService;
    }

    @Override
    @RequirePermission("article:create")
    public ArticleVO create(ArticleCreateDTO dto) {
        Category category = categoryMapper.selectById(dto.getCategoryId());

        if (category == null) {
            throw new BusinessException(404, "分类不存在,不能添加");
        }
        Article article = articleConverter.toEntity(dto);

        Long currentUserId = UserContext.getUserId();
        article.setUserId(currentUserId);

        articleMapper.insert(article);
        return articleMapper.selectVOById(article.getId());
    }

    @Override
    public PageVO<ArticleVO> page(ArticleQueryDTO query) {
        Page<ArticleVO> page = new Page<>(query.getPage(), query.getPageSize());
        List<String> permission = permissionMapper.selectCodesByUserId(1L);
        System.out.println(permission);
        IPage<ArticleVO> result = articleMapper.selectVOPage(page, query);

        PageVO<ArticleVO> pageVO = new PageVO<>();

        pageVO.setTotal(result.getTotal());
        pageVO.setPage(result.getCurrent());
        pageVO.setPageSize(result.getSize());
        pageVO.setRecords(result.getRecords());

        return pageVO;
    }

    @Override
    public ArticleVO update(Long id, ArticleUpdateDTO dto) {
        Article article = articleMapper.selectById(id);

        if (article == null) {

            throw new BusinessException(404, "文章不存在");

        }
        PermissionUtils.checkOwnerAny(article.getUserId(), "article:update");
        articleConverter.updateEntity(dto, article);

        articleMapper.updateById(article);
        return articleConverter.toVo(article);
    }

    @Override
    @RequirePermission("article:delete")
    public void delete(Long id) {
        Article article = articleMapper.selectById(id);

        if(article == null) {
            throw new BusinessException(404, "文章不存在");
        }

        PermissionUtils.checkOwnerAny(article.getUserId(), "article:delete:any");

        articleMapper.deleteById(id);
    }

    @Override
    public ArticleVO getById(Long id) {
        ArticleVO article = articleMapper.selectVOById(id);
        if(article == null) {
            throw new BusinessException(404,"文章不存在");
        }
        Long increment = articleViewService.increment(id);
        article.setViewCount(article.getViewCount() + increment);
        return article;
    }
}
