package indi.repo.springboot.module;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ChenHQ
 * @date: create in 2021/6/27
 */
@Data
public class StudentDTO {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "性别不能为空")
    private String sex;

}
