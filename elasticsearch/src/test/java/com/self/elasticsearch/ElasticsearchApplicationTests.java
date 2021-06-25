package com.self.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.self.elasticsearch.model.Account;
import com.self.elasticsearch.service.AccountService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
     *
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
    public void testQuery2() throws Exception {

        SearchRequest request = new SearchRequest();

        request.indices("account");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.matchQuery("age", 28));

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

        printResult(search);


    }

    private static void printResult(SearchResponse search) {
        SearchHit[] hits = search.getHits().getHits();

//        List<Account> accountList = new ArrayList<>(1200);

        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            Account account = JSON.parseObject(sourceAsString, Account.class);
            account.setId(Integer.parseInt(hit.getId()));
            System.out.println("account = " + account);
//            accountList.add(account);
        }

//        accountService.saveBatch(accountList);
    }

    public static SearchRequest searchRequest = null;
    public static SearchSourceBuilder builder = null;

    @BeforeAll
    public static void before() {
        searchRequest = new SearchRequest();
        searchRequest.indices("bank");
        builder = new SearchSourceBuilder();
    }

    @Test
    public void test4() {
        builder.query(new TermQueryBuilder("gender.keyword","F"));
    }


    @Test
    public void test5() {
        builder.query(new TermQueryBuilder("gender.keyword","F"));
    }

    //查询所有的女性员工，年龄大于30 且 工资 大于 30000
    @Test
    public void test6(){

        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.must(new TermQueryBuilder("gender.keyword","F"))
                .must(new RangeQueryBuilder("age").gt(30))
                .must(new RangeQueryBuilder("balance").gt(30000));

        builder.query(queryBuilder);
        builder.sort("account_number", SortOrder.ASC);

    }



    //-- 根据男女分组，求男女各平均工资
    //SELECT gender,COUNT(1) as num,SUM(balance) as balanceAll,  AVG(balance) as 'avg' FROM account GROUP BY gender;
    @Test
    public void test7() {

        TermsAggregationBuilder aggregationBuilder = new TermsAggregationBuilder("sexAggs", ValueType.STRING);
        aggregationBuilder.field("gender.keyword").size(10).subAggregation(new AvgAggregationBuilder("avgBalance").field("balance"));
        builder.aggregation(aggregationBuilder);
    }


    //-- 根据年龄分组，求各年龄段平均工资
    //SELECT age,AVG(balance),SUM(balance) FROM account GROUP BY age;
    @Test
    public void test8() {
        TermsAggregationBuilder aggregationBuilder = new TermsAggregationBuilder("ageAggs", ValueType.NUMBER);
        aggregationBuilder.field("age").size(20).subAggregation(new AvgAggregationBuilder("avgBalance").field("balance"));
        builder.aggregation(aggregationBuilder);
    }

    //-- 根据性别、年龄 分组，对各年龄段平均工资求数
    //SELECT gender,age, AVG(balance) FROM account GROUP BY gender,age;
    @Test
    public void test9() {
        TermsAggregationBuilder aggregationBuilder = new TermsAggregationBuilder("genderAgg", ValueType.STRING);
        aggregationBuilder.field("gender.keyword").size(10)
                .subAggregation(new TermsAggregationBuilder("ageAgg", ValueType.NUMBER).field("age")
                        .subAggregation(new AvgAggregationBuilder("avgBalance").field("balance")));
        builder.aggregation(aggregationBuilder);
    }



    @AfterAll
    public void after() {
        exec();
    }


    public  void exec() {
        System.out.println(builder);
        searchRequest.source(builder);
        SearchResponse search = null;
        try {
            search = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        printResult(search);
    }


}
