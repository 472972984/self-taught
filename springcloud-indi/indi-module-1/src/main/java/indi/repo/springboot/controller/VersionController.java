package indi.repo.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenHQ
 * @date 2025/12/30 10:23
 * 版本信息控制器，展示Git提交记录
 */
@RestController
@Configuration
@Slf4j
public class VersionController {

    @Value("${git.branch:unknown}")
    private String branch;

    @Value("${git.commit.id.abbrev:unknown}")
    private String commitId;

    @Value("${git.commit.time:unknown}")
    private String commitTime;

    @Value("${git.commit.message.short:unknown}")
    private String commitMessage;

    /**
     * 暴露接口返回版本信息
     */
    @GetMapping("/version")
    public String getVersion() {
        return String.format(
                "分支：%s\n提交ID：%s\n提交时间：%s\n提交信息：%s",
                branch, commitId, commitTime, commitMessage
        );
    }
}