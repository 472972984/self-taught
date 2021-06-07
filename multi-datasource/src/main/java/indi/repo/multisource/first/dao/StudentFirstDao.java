package indi.repo.multisource.first.dao;

import indi.repo.multisource.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */
@Mapper
public interface StudentFirstDao {

    /**
     * 通过id获取数据
     * @param id
     * @return
     */
    @Select("select * from student where id = #{id}")
//    @SourceTypeAnnotation(DBTypeEnum.second)
    Student getById(@Param("id") Long id);


}
