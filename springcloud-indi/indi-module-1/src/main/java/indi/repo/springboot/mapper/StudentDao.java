package indi.repo.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.repo.springboot.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 */
public interface StudentDao extends BaseMapper<Student> {

    List<Student> selectAll();

    void insertBatchTest(List<Student> list);

    Student selectByIdCustom(@Param("id") Long id);

}
