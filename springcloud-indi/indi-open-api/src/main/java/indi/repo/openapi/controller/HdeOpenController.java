package indi.repo.openapi.controller;

import indi.repo.openapi.base.HdeRequest;
import indi.repo.openapi.common.Result;
import indi.repo.openapi.core.context.LocalHandleContext;
import indi.repo.openapi.factory.HdeOpenService;
import indi.repo.openapi.factory.OpenClient;
import indi.repo.student.StudentRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenHQ
 * @description: 开放api统一入口
 * @date 2021/12/21 11:03
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class HdeOpenController {

    @Autowired
    private OpenClient openClient;

    /**
     * 开放API统一入口
     * @param request
     * @return
     */
    @PostMapping("/entrance")
    public Result entrance(HdeRequest request) {
        String method = LocalHandleContext.getHandleContext().getMethod();
        HdeOpenService serviceByMethod = openClient.getServiceByMethod(method);
        return serviceByMethod.execute(request);
    }



    @GetMapping("/test")
    public void test(){
        System.out.println("进来了");
    }


}
