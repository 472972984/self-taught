package indi.repo.openapi.module;

import indi.repo.module.OrderInformDTO;
import indi.repo.openapi.annotation.OpenApiParam;
import indi.repo.openapi.base.HdeRequest;
import indi.repo.openapi.constant.MethodMappingConstant;
import lombok.Data;

/**
 * @author ChenHQ
 * @description: TODO
 * @date 2022/4/1 16:51
 */
@Data
@OpenApiParam(value = MethodMappingConstant.PUSH_ORDER, paramRpcClass = OrderInformDTO.class)
public class OrderInformRequest extends OrderInformDTO implements HdeRequest {
}
