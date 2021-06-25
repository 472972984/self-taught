package com.self.elasticsearch.model;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/25
 * @desc:
 */
import lombok.Data;

@Data
public class Article {

    private Integer id;
    private String author;
    private String title;
    private String path;
    private String content;
    private String fileFingerprint;

}


