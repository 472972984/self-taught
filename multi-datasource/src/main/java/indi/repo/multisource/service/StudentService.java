package indi.repo.multisource.service;

import indi.repo.multisource.entity.Student;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */
public interface StudentService {

    /**
     * 通过id获取数据
     * @param id
     * @return
     */
    Student getById(Long id);

    /**
     * 通过id获取数据,使用注解
     * @param id
     * @return
     */
    Student getByIdWithAn(Long id);

}
