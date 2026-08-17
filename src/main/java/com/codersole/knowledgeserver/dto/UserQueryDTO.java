package com.codersole.knowledgeserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(name = "UserQueryDTO", description = "用户分页查询参数")
public class UserQueryDTO {

    @Schema(description = "页码", example = "1", defaultValue = "1")
    private Long page = 1L;
    @Schema(description = "每页条数", example = "10", defaultValue = "10")
    private Long pageSize = 10L;

    @Schema(description = "最小年龄", example = "18", minimum = "0")
    @Min(value = 0, message = "年龄最小值不能小于0")
    private Integer minAge;
    @Schema(description = "最大年龄", example = "30", maximum = "150")
    @Max(value = 150, message = "年龄最大值不能大于150")
    private Integer maxAge;


    @Schema(description = "排序字段", example = "age", allowableValues = {"id", "name", "age"})
    private String sortBy;
    @Schema(description = "排序方向", example = "desc", allowableValues = {"asc", "desc"})
    private String order;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    @Schema(description = "用户名关键字", example = "张")
    private String name;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
