package indi.repo.openapi.module;

import indi.repo.module.StudentQueryDTO;
import indi.repo.openapi.annotation.OpenApiParam;
import indi.repo.openapi.base.HdeRequest;
import indi.repo.openapi.constant.MethodMappingConstant;
import lombok.Data;

/**
 * @author ChenHQ
 * @date: create in 2021/12/26
 */
@Data
@OpenApiParam(value = MethodMappingConstant.QUERY_STUDENT, paramRpcClass = StudentQueryDTO.class)
public class StudentRequest extends StudentQueryDTO implements HdeRequest {
}
