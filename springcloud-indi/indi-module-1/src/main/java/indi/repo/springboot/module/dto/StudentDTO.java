package indi.repo.springboot.module.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author ChenHQ
 * @date: create in 2021/6/27
 */
@Data
public class StudentDTO {

    @NotNull(message = "用户名不能为空")
    @ExcelProperty("用户名")
    private String username;

    @NotNull(message = "性别不能为空")
    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("生日（yyyy-MM-dd）")
    private Date birthday;

}
