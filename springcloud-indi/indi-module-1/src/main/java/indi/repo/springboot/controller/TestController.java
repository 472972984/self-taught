package indi.repo.springboot.controller;

import indi.repo.springboot.entity.Student;
import indi.repo.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 * @desc:
 */
@RestController
@RequestMapping("test")
@RefreshScope
public class TestController {

    @Value("${swagger2.enable:false}")
    private String enable;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试项目启动web访问
      * @return
     */
    @GetMapping("/hello")
    public String test() {
        System.out.println("enable = " + enable);
        return "world";
    }

    /**
     * 整合mybatis-plus 测试访问
     * @param id
     * @return
     */
    @GetMapping("/student/{id}")
    public String test(@PathVariable Long id) {
        Student student = studentService.queryStudent(id);
        System.out.println("student = " + student);
        return "world";
    }

    /**
     * 全局异常捕获
     * @return
     */
    @GetMapping("/throwException")
    public String exception() {

        throw new RuntimeException("异常信息");
    }


}
