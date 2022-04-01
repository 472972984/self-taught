package indi.repo.openapi.controller;

import indi.repo.common.Result;
import indi.repo.openapi.base.HdeRequest;
import indi.repo.openapi.core.context.LocalHandleContext;
import indi.repo.openapi.factory.HdeOpenService;
import indi.repo.openapi.factory.OpenClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ChenHQ
 * @description: 开放api统一入口
 * @date 2021/12/21 11:03
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class HdeOpenController {

    @Resource
    private OpenClient openClient;

    /**
     * 开放API统一入口   POST请求
     * @param request  统一参数
     */
    @PostMapping("entrance")
    public Result<Object> entrancePost(HdeRequest request) {
        String method = LocalHandleContext.getHandleContext().getMethod();
        HdeOpenService serviceByMethod = openClient.getServiceByMethod(method);
        return serviceByMethod.execute(request);
    }

    /**
     * 开放API统一入口    GET请求
     * @param request  统一参数
     */
    @GetMapping("entrance")
    public Result<Object> entranceGet(HdeRequest request) {
        String method = LocalHandleContext.getHandleContext().getMethod();
        HdeOpenService serviceByMethod = openClient.getServiceByMethod(method);
        return serviceByMethod.execute(request);
    }



}
