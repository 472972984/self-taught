package indi.repo.studentservice.controller;

import cn.hutool.core.bean.BeanUtil;
import indi.repo.common.Result;
import indi.repo.module.StudentQueryDTO;
import indi.repo.module.StudentVO;
import indi.repo.studentservice.module.StudentDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ChenHQ
 * @date: create in 2021/6/27
 */
@RestController
public class StudentController {

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        System.out.println("request = " + request);
        System.out.println("我来了！！！");
        return "chenhuiqi";
    }

    /**
     * 查询学生信息
     * @param studentQueryDTO
     * @return
     */
    @PostMapping("/query")
    public Result<StudentVO> queryStudent(@RequestBody StudentQueryDTO studentQueryDTO) {
        System.out.println("studentQueryDTO = " + studentQueryDTO);
        StudentDO build = StudentDO.builder().id(1L).sex("男").username("陈汇奇").build();
        return Result.ok(BeanUtil.copyProperties(build,StudentVO.class));
    }

}
