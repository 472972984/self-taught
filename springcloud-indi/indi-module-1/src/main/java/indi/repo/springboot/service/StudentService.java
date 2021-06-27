package indi.repo.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import indi.repo.springboot.entity.Student;

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
}
