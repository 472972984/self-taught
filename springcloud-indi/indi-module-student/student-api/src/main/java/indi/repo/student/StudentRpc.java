package indi.repo.student;

import indi.repo.common.Result;
import indi.repo.module.StudentQueryDTO;
import indi.repo.module.StudentVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ChenHQ
 * @date: create in 2021/12/26
 */
@FeignClient(value = "indi-student")
public interface StudentRpc {

    @PostMapping("/query")
    Result<StudentVO> queryStudent(@RequestBody StudentQueryDTO studentQueryDTO);


}
