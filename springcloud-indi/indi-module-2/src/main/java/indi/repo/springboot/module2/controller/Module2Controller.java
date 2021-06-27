package indi.repo.springboot.module2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ChenHQ
 * @date: create in 2021/6/27
 */
@RestController
@RequestMapping("/module2")
public class Module2Controller {

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        System.out.println("request = " + request);
        System.out.println("我来了！！！");
        return "chenhuiqi";
    }

}
