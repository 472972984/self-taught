package com.self.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ChenHQ
 * 系统名称: elasticsearch
 * 模块名称:
 * 类 名 称: ElasticsearchConfig
 * 软件版权: 杭州数美科技有限公司
 * 功能说明：
 * 系统版本：
 * 开发时间: create in 2020/8/24
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期 修改人员 修改说明
 */
@Configuration
public class ElasticsearchConfig {

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
       /* builder.addHeader("Authorization", "Bearer " + TOKEN);
        builder.setHttpAsyncResponseConsumerFactory(
                new HttpAsyncResponseConsumerFactory
                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));*/
        COMMON_OPTIONS = builder.build();
    }
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient highLevelClient = new RestHighLevelClient(RestClient.builder(
                new HttpHost("192.168.154.128", 9200, "http")
        ));
        return highLevelClient;
    }



}
