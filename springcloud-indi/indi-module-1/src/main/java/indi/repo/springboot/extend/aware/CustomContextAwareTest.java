package indi.repo.springboot.extend.aware;

import indi.repo.springboot.entity.Student;
import indi.repo.springboot.mapper.StudentDao;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ChenHQ
 * @date 2023/9/14 15:05
 */
@Component
public class CustomContextAwareTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void test() {
        StudentDao bean = applicationContext.getBean(StudentDao.class);
        List<Student> students = bean.selectAll();
        System.out.println("students = " + students);
    }

}
