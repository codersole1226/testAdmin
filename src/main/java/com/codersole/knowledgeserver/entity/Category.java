package com.codersole.knowledgeserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private String description;

    private LocalDateTime createTime;

//    public Category() {
//    }
//
//    public Category(Long id, String name, String description, LocalDateTime createTime) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.createTime = createTime;
//    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
