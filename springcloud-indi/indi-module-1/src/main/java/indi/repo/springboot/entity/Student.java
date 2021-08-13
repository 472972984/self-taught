package indi.repo.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 * @desc:
 */
@TableName("student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String sex;


}
