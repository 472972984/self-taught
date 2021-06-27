package indi.repo.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.repo.springboot.context.HandleContext;
import indi.repo.springboot.context.LocalHandleContext;
import indi.repo.springboot.entity.Student;
import indi.repo.springboot.mapper.StudentDao;
import indi.repo.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
}
