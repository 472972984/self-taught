package com.self.elasticsearch;

import com.self.elasticsearch.dao.UserMapper;
import com.self.elasticsearch.model.User;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchApplicationTests {

	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;

	@Test
	public void contextLoads() {

		System.out.println("elasticsearchTemplate = " + elasticsearchTemplate);

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.queryStringQuery("spring boot OR 书籍")).build();

		long count = elasticsearchTemplate.count(searchQuery);
		System.out.println("count = " + count);

	}

	@Autowired
	UserMapper	userMapper;

	@Test
	public void test(){

		Iterable<User> all = userMapper.findAll();
		System.out.println("all = " + all);

	}


}
