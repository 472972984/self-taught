package indi.repo.springboot.controller;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import indi.repo.common.annotation.RepeatSubmit;
import indi.repo.common.exception.BaseException;
import indi.repo.common.exception.enums.DemoExcepEnum;
import indi.repo.common.module.Result;
import indi.repo.common.utils.HibernateValidatorUtils;
import indi.repo.springboot.common.cache.MemoryCache;
import indi.repo.springboot.core.context.HandleContext;
import indi.repo.springboot.core.context.LocalHandleContext;
import indi.repo.springboot.entity.Student;
import indi.repo.springboot.feign.api.TestApi;
import indi.repo.springboot.mapper.StudentDao;
import indi.repo.springboot.module.dto.StudentDTO;
import indi.repo.springboot.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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
@Slf4j
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
     *
     * @return
     */
    @GetMapping("/hello")
    public String test() {
        System.out.println("enable = " + enable);
        return "world";
    }

    /**
     * 测试上传文件
     * @param file
     * @param name
     * @return
     */
    @PostMapping("/file")
    public String testFile(MultipartFile file, String name) {
        System.out.println("name = " + name);
        return name;
    }


    /**
     * 整合mybatis-plus 测试访问
     *
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
     *
     * @return
     */
    @GetMapping("/student/getAll")
    public Result<List<Student>> test22() {
        return Result.ok((List<Student>) MemoryCache.getCache("studentAll", Lists.newArrayList(), o -> {
            log.info("hit from db");
            return studentDao.selectAll();
        }));
    }

    /**
     * 全局异常捕获
     *
     * @return
     */
    @GetMapping("/throwException")
    public String exception() {
        System.out.println(redisTemplate);
        throw new RuntimeException("异常信息");
    }

    /**
     * 全局异常捕获
     *
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
        studentService.testAsync(context.getUserId(), context.getTraceId());
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

    @PostMapping("/upload")
    public void upload(MultipartFile studentExcel) {
//        EasyExcel.
    }

    @PostMapping("/read")
    public void read() throws Exception {
        File file = new File("C:\\Users\\Thompson\\Desktop\\导入Excel.xlsx");
        List<StudentDTO> studentList = EasyExcel.read(file, StudentDTO.class, null).sheet().head(StudentDTO.class).doReadSync();
        System.out.println(studentList);

        List<StudentDTO> objects = EasyExcel.read(new FileInputStream(file)).sheet(0).head(StudentDTO.class).doReadSync();
        System.out.println();
    }

    public static void main(String[] args) throws Exception {


    }

    @GetMapping("/test/aop")
    public void testAop(){
        studentService.updateById(StudentDTO.builder().id(1L).username("chen").sex("man").build());
    }





}
