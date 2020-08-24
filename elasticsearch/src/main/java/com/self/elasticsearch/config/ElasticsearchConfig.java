package com.self.elasticsearch.config;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

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

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client) {
        return new ElasticsearchTemplate(client);
    }

}
