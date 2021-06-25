package indi.repo.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.repo.springboot.entity.Student;
import indi.repo.springboot.mapper.StudentDao;
import indi.repo.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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
}