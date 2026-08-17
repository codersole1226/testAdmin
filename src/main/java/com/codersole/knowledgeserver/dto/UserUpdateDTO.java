package com.codersole.knowledgeserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "UserUpdateDTO", description = "更新用户请求")
public class UserUpdateDTO {
    @Schema(description = "用户名", example = "李四", maxLength = 20)
    @NotBlank(message = "用户名不能为空")
    @Size(max = 20, message = "用户名长度不能超过20个字符")
    private String name;

    @Schema(description = "年龄", example = "25", minimum = "0", maximum = "150")
    @NotNull(message = "年龄不能为空")
    @Min(value = 0, message = "年龄不能小于0")
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

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "UserUpdateDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
