package indi.repo.springboot.controller;

import indi.repo.springboot.common.Result;
import indi.repo.springboot.common.utils.HibernateValidatorUtils;
import indi.repo.springboot.feign.api.TestApi;
import indi.repo.springboot.common.annotation.RepeatSubmit;
import indi.repo.springboot.common.exception.BaseException;
import indi.repo.springboot.common.exception.enums.DemoExcepEnum;
import indi.repo.springboot.context.HandleContext;
import indi.repo.springboot.context.LocalHandleContext;
import indi.repo.springboot.entity.Student;
import indi.repo.springboot.mapper.StudentDao;
import indi.repo.springboot.module.StudentDTO;
import indi.repo.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TestApi testApi;

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
     * 整合mybatis-plus 测试访问
     * @return
     */
    @GetMapping("/student/getAll")
    public Result<List<Student>> test22() {
        List<Student> students = studentDao.selectAll();
        return Result.ok(students);
    }

    /**
     * 全局异常捕获
     * @return
     */
    @GetMapping("/throwException")
    public String exception() {
        System.out.println(redisTemplate);
        throw new RuntimeException("异常信息");
    }

    /**
     * 全局异常捕获
     * @return
     */
    @GetMapping("/throwException2")
    public String exception2() {
        throw new BaseException(DemoExcepEnum.ERROR_TEST);
    }


    @GetMapping("/context")
    public String context() {
        HandleContext context = LocalHandleContext.getHandleContext();
        System.out.println(context.getTraceId());
        System.out.println(context.getDate());
        return "context";
    }

    @PostMapping("/repeat")
    @RepeatSubmit
    public Result<String> repeat(@RequestBody Student student) {
        System.out.println("student = " + student);
        return Result.ok();
    }

    @GetMapping("/testAsync")
    public String testAsync() {
        HandleContext context = LocalHandleContext.getHandleContext();
        System.out.println(context.getTraceId());
        System.out.println(context.getDate());
        studentService.testAsync(context.getUserId(),context.getTraceId());
        return "context";
    }

    @GetMapping("/testValid")
    public String testValid(@Validated StudentDTO studentDTO) {
        System.out.println("studentDTO = " + studentDTO);
        return "context";
    }

    @PostMapping("/testValidList")
    public String testValidList(@RequestBody @Validated List<StudentDTO> studentDTOS) {
        System.out.println("studentDTOS = " + studentDTOS);

        studentDTOS.forEach(HibernateValidatorUtils::fastFailValidate);
        return "context";
    }

    @GetMapping("/test/api")
    public String testApi() {
        String test = testApi.test();
        System.out.println("test = " + test);
        return "context";
    }

}
