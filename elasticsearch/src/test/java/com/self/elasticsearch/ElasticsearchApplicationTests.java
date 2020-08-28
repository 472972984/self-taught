package com.self.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.self.elasticsearch.config.ElasticsearchConfig;
import com.self.elasticsearch.model.User;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ElasticsearchApplicationTests {

	@Autowired
	private RestHighLevelClient client;

	/**
	 * 新增一个索引 users ，并插入一条数据
	 * @throws IOException
	 */
	@Test
	public void testIndex() throws IOException {
		IndexRequest indexRequest = new IndexRequest("users");

		indexRequest.id("1"); //数据id

		User user = new User("zhangsan",22,"Man");

		String jsonString = JSON.toJSONString(user);

		indexRequest.source(jsonString, XContentType.JSON);

		IndexResponse response = client.index(indexRequest, ElasticsearchConfig.COMMON_OPTIONS);

		System.out.println("response = " + response);

	}



	@Test
	public void contextLoads() {

		System.out.println("client = " + client);

	}


}
