package indi.repo.multisource.service.impl;

import indi.repo.multisource.config.SourceTypeAnnotation;
import indi.repo.multisource.entity.Student;
import indi.repo.multisource.first.dao.StudentFirstDao;
import indi.repo.multisource.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentFirstDao studentFirstDao;


    @Override
    public Student getById(Long id) {
        return studentFirstDao.getById(id);
    }

    @SourceTypeAnnotation
    @Override
    public Student getByIdWithAn(Long id) {
        return studentFirstDao.getById(id);
    }
}
