package indi.repo.springboot.controller;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;

/**
 * @author ChenHQ
 * @date 2023/5/17 15:48
 */
@BaseRequest(baseURL = "http://localhost:8080")
public interface AgentClient {

    @Get(url = "/test/hello")
    String executeTask();

}
