package indi.repo.openapi.factory.service;

import indi.repo.common.Result;
import indi.repo.openapi.base.HdeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ChenHQ
 * @description: 订单开放API
 * @date 2021/12/21 9:37
 */
@Slf4j
@Service
//@OpenApiEvent({MethodMappingConstant.HDE_ORDER_ITEM_GET})
public class OrderOpenService extends AbstractOpenService {

    //@Autowired
    //private OrderRpc orderRpc;


    /**
     * 根据反射进行优化
     * 根据接口名称【method：hde.order.item.get，则方法名：hdeOrderItemGet】进行区分
     * @param request     请求参数
     * @param <T>
     * @return
     */
    @Override
    public <T extends Result> T execute(HdeRequest request) {
        return null;
//        return execute(request,orderRpc);
    }


}
