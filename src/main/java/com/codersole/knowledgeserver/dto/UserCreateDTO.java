package com.codersole.knowledgeserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "UserCreateDTO", description = "创建用户请求")
public class UserCreateDTO {
    @Schema(description = "用户名", example = "张三", maxLength = 20)
    @NotBlank(message="用户名不能为空")
    @Size(max = 20, message = "用户名长度不能超过20个字符")
    private String name;

    @Schema(description = "年龄", example = "18", minimum = "0", maximum = "150")
    @NotNull(message =  "年龄不能为空")
    @Min(value = 0,message = "年龄不能小于0")
    @Max(value = 150, message = "年龄不能大于150")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
