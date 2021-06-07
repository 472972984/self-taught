package indi.repo.multisource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */
@Data
@TableName("student")
public class Student {

    private Long id;

    private String username;

    private String sex;

}
