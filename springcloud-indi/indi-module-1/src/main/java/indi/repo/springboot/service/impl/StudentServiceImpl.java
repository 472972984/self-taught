package indi.repo.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.repo.springboot.core.context.HandleContext;
import indi.repo.springboot.core.context.LocalHandleContext;
import indi.repo.springboot.entity.Student;
import indi.repo.springboot.mapper.StudentDao;
import indi.repo.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 * @desc:
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public Student queryStudent(Long id) {
        return studentDao.selectById(id);
    }

    @Override
    @Async
    public void testAsync(String userId,String tranceId) {

        try {
            Thread.sleep(2000);
            HandleContext handleContext = LocalHandleContext.getHandleContext();
            System.out.println("handleContext = " + handleContext);
            System.out.println();
            System.out.println("userId = " + userId);
            System.out.println("tranceId = " + tranceId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量插入500条数据  使用：saveBatch  不开启：rewriteBatchedStatements=true  耗时：19626毫秒
     * 批量插入500条数据  使用：saveBatch  开启：rewriteBatchedStatements=true  耗时：  1215毫秒
     */
    @Override
    public void batchInsertTest1() {
        List<Student> studentList = new ArrayList<>(500);
        for (int i = 0; i < 500; i++) {
            studentList.add(new Student(Long.parseLong(i + ""), "username", "sex"));
        }
        long start = System.currentTimeMillis();
        this.saveBatch(studentList);
        long end = System.currentTimeMillis();
        System.out.println("插入500条数据耗时：" + (end - start) + "毫秒");
    }

    /**
     * 批量插入500条数据  使用：studentDao.insert  不开启：rewriteBatchedStatements=true  31260毫秒
     */
    @Override
    public void batchInsertTest2() {
        List<Student> studentList = new ArrayList<>(500);
        for (int i = 0; i < 500; i++) {
            studentList.add(new Student(Long.parseLong(i + ""), "username", "sex"));
        }
        long start = System.currentTimeMillis();
        studentList.forEach(student -> {
            studentDao.insert(student);
        });
        long end = System.currentTimeMillis();
        System.out.println("插入500条数据耗时：" + (end - start) + "毫秒");
    }

    /**
     * 批量插入500条数据  使用：自定义for  不开启：rewriteBatchedStatements=true  1221毫秒
     * 批量插入500条数据  使用：自定义for  开启：rewriteBatchedStatements=true    1263毫秒
     */
    @Override
    public void batchInsertTest3() {
        List<Student> studentList = new ArrayList<>(500);
        for (int i = 0; i < 500; i++) {
            studentList.add(new Student(Long.parseLong(i + ""), "username", "sex"));
        }
        long start = System.currentTimeMillis();
        studentDao.insertBatchTest(studentList);
        long end = System.currentTimeMillis();
        System.out.println("插入500条数据耗时：" + (end - start) + "毫秒");
    }
}
