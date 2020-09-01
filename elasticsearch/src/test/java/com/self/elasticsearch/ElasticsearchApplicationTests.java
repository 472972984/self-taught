package com.self.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.self.elasticsearch.model.Account;
import com.self.elasticsearch.model.User;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ElasticsearchApplicationTests {

	@Autowired
	private RestHighLevelClient client;



	/**
	 * 从 es 中检索数据
	 */
	@Test
	public void searchData() throws IOException {
		//创建检索请求
		SearchRequest searchRequest = new SearchRequest();
		//指定索引
		searchRequest.indices("bank");

		//指定DSL 检索条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());

//		searchSourceBuilder.from(0);
//		searchSourceBuilder.size(5);

		AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageAgg").field("age");
		searchSourceBuilder.aggregation(aggregationBuilder);

		//查询条件
		System.out.println("searchSourceBuilder = " + searchSourceBuilder);

		searchRequest.source(searchSourceBuilder);

		//执行检索
		SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);

		//分析结果
		System.out.println("searchResponse = " + searchResponse);

		SearchHits hits = searchResponse.getHits();

		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			//System.out.println("hit = " + hit);

			System.out.println("===================================");

			String sourceAsString = hit.getSourceAsString();

			Account account = JSON.parseObject(sourceAsString, Account.class);

			System.out.println("account = " + account);

		}




	}


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

		IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

		System.out.println("response = " + response);

	}



	@Test
	public void contextLoads() {

		System.out.println("client = " + client);

	}


	@Test
	public void testQuery() throws IOException {
		SearchRequest searchRequest = new SearchRequest();

		//设置
		searchRequest.indices("account");

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchQuery("address","mill"));

		sourceBuilder.aggregation(AggregationBuilders.avg("ageAgg").field("age"));

		System.out.println("sourceBuilder = " + sourceBuilder);

		searchRequest.source(sourceBuilder);


		SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

	}




}
