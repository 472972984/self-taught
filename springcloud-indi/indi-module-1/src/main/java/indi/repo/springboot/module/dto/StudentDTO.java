package indi.repo.springboot.module.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import indi.repo.springboot.common.log.annotation.ImportantField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author ChenHQ
 * @date: create in 2021/6/27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    @NotNull(message = "用户名不能为空")
    @ExcelProperty("用户名")
    @ImportantField(title = "用户名")
    private String username;

    @NotNull(message = "性别不能为空")
    @ExcelProperty("性别")
    @ImportantField(title = "性别")
    private String sex;

    private Long id;



}
