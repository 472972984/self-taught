package indi.repo.openapi.factory;

import indi.repo.common.Result;
import indi.repo.openapi.base.HdeRequest;

/**
 * @author ChenHQ
 * @description: 统一执行入口
 * @date 2021/12/21 9:30
 */
public interface HdeOpenService {

    /**
     * 统一执行入口
     * @param request     请求参数
     * @param <T>         T：泛型返回值
     * @return
     */
     <T extends Result> T execute(HdeRequest request);

}
