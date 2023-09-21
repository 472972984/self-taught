package indi.repo.test;

import indi.repo.common.utils.SpringUtils;
import indi.repo.springboot.SpringBootDemoApplication;
import indi.repo.springboot.extend.aware.CustomContextAwareTest;
import indi.repo.springboot.extend.aware.EnvironmentAwareTest;
import indi.repo.springboot.service.StudentService;
import indi.repo.springboot.service.TestBeanService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author ChenHQ
 * @date 2023/9/14 15:09
 */
@SpringBootTest(classes = {SpringBootDemoApplication.class})
@Slf4j
public class ApplicationTest {

    @Resource
    private CustomContextAwareTest contextAwareTest;

    @Resource
    private EnvironmentAwareTest environmentAwareTest;

    @Resource
    private StudentService studentService;


    @Test
    void contextLoads() {
    }

    @Test
    public void test4() {
        TestBeanService testBeanService = SpringUtils.getBean(TestBeanService.class);
        testBeanService.test();
        TestBeanService testBeanService1 = SpringUtils.getBean(TestBeanService.class);
        testBeanService1.test();
    }

    @Test
    public void test3() {
    }

    @Test
    public void test2() {
        environmentAwareTest.test();
    }

    @Test
    public void test1() {
        contextAwareTest.test();
    }

}
