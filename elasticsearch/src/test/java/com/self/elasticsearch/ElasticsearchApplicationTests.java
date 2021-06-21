package com.self.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.self.elasticsearch.dao.AccountDao;
import com.self.elasticsearch.model.Account;
import com.self.elasticsearch.service.AccountService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ElasticsearchApplicationTests {

	@Autowired
	private RestHighLevelClient client;



	/**
	 * 从 es 中检索数据
	 */
	/*@Test
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




	}*/


	/**
	 * 新增一个索引 users ，并插入一条数据
	 * @throws IOException
	 */
	/*@Test
	public void testIndex() throws IOException {
		IndexRequest indexRequest = new IndexRequest("users");

		indexRequest.id("1"); //数据id

		User user = new User("zhangsan",22,"Man");

		String jsonString = JSON.toJSONString(user);

		indexRequest.source(jsonString, XContentType.JSON);

		IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

		System.out.println("response = " + response);

	}*/


	@Autowired
	private AccountService accountService;

	@Test
	public void contextLoads() {



	}


/*	@Test
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

	}*/

	@Test
	public void testQuery2() throws Exception{

		SearchRequest request = new SearchRequest();

		request.indices("account");

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

		sourceBuilder.query(QueryBuilders.matchQuery("age",28));

		request.source(sourceBuilder);

		SearchResponse response = client.search(request, RequestOptions.DEFAULT);

		System.out.println("response = " + response);

		SearchHits hits = response.getHits();

		SearchHit[] searchHits = hits.getHits();

		for (SearchHit searchHit : searchHits) {
			//System.out.println("searchHit = " + searchHit);
			Account account = JSON.parseObject(searchHit.getSourceAsString(), Account.class);
			System.out.println("account = " + account);
		}

	}

	@Test
	public void test3() throws IOException {

		SearchRequest searchRequest = new SearchRequest();

		searchRequest.indices("bank");

		SearchSourceBuilder builder = new SearchSourceBuilder();

//		MatchQueryBuilder builder1 = QueryBuilders.matchQuery("age", "28");

//		builder.query(builder1);

		builder.from(0).size(1000);

		System.out.println("builder = " + builder);

		searchRequest.source(builder);


		SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

		System.out.println("search = " + search);

		SearchHit[] hits = search.getHits().getHits();

		List<Account> accountList = new ArrayList<>(1200);

		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Account account = JSON.parseObject(sourceAsString, Account.class);
			account.setId(Integer.parseInt(hit.getId()));
			accountList.add(account);
		}

		accountService.saveBatch(accountList);



	}





}
