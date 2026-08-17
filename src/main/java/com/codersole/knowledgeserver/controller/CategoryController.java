package com.codersole.knowledgeserver.controller;

import com.codersole.knowledgeserver.common.Result;
import com.codersole.knowledgeserver.dto.CategoryCreateDTO;
import com.codersole.knowledgeserver.dto.CategoryUpdateDTO;
import com.codersole.knowledgeserver.service.CategoryService;
import com.codersole.knowledgeserver.vo.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "分类管理")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询分类")
    public Result<CategoryVO> getCategory(@Parameter(description = "分类ID", example = "1") @PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }
    @GetMapping
    @Operation(summary = "查询全部分类")
    public Result<List<CategoryVO>> list() {
        return Result.success(categoryService.list());
    }

    @PostMapping
    @Operation(summary = "创建分类")
    public Result<CategoryVO> create(@Parameter(description = "分类创建信息") @Valid @RequestBody CategoryCreateDTO dto) {
        return Result.success(categoryService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新分类")
    public Result<CategoryVO> update(
            @Parameter(description = "分类ID", example = "1") @PathVariable Long id,
            @Parameter(description = "分类更新信息") @Valid @RequestBody CategoryUpdateDTO dto
    ) {
        return Result.success(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除分类")
    public Result<Void> delete(@Parameter(description = "分类ID", example = "1") @PathVariable Long id) {
        categoryService.delete(id);
        return Result.success();
    }
}
