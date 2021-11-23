package indi.repo.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import indi.repo.springboot.entity.Student;
import indi.repo.springboot.module.dto.StudentDTO;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 * @desc:
 */
public interface StudentService extends IService<Student> {

    Student queryStudent(Long id);

    void testAsync(String userId, String tranceId);

    void batchInsertTest1();

    void batchInsertTest2();

    void batchInsertTest3();

    void updateById(StudentDTO studentDTO);

}
