package indi.repo.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 */
@TableName("student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Comparable<Student> {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String sex;

    private Parent parent;

    public Student(Long id, String username, String sex) {
        this.id = id;
        this.username = username;
        this.sex = sex;
    }

    @Override
    public int compareTo(Student o) {
        return this.id.intValue() - o.id.intValue();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Parent {
        private String name;

        private int age;
    }


}
