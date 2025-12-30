package indi.repo.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 */
@RestController
@RequestMapping("")
@RefreshScope
@Slf4j
public class TestController {


    public static void main(String[] args) {


    }

    @GetMapping("/test")
    public String test(HttpServletResponse response) throws IOException {
        return "success";
    }


}
