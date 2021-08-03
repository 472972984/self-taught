package com.self.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.self.elasticsearch.model.Account;
import com.self.elasticsearch.model.Article;
import com.self.elasticsearch.service.AccountService;
import com.self.elasticsearch.utils.WordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
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

//		T builder1 = QueryBuilders.matchQuery("age", "28");

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

//    @BeforeAll
    public static void before() {
        searchRequest = new SearchRequest();
        searchRequest.indices("bank");
        builder = new SearchSourceBuilder();
    }

//    @AfterAll
    public void after() {
        exec();
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

    @Test
    public void test10() {

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


    /**
     * 上传txt文件类型文档
     */
    @Test
    public void uploadTxtFile() throws Exception{

        File file = new File("C:\\Users\\Thompson\\Desktop\\es\\AppletTicketTypeController.java");

        StringBuffer result = new StringBuffer();

        //1、读取文件内容
        try (FileInputStream in = new FileInputStream(file)) {
            Long filelength = file.length();
            byte[] filecontent = new byte[filelength.intValue()];
            in.read(filecontent);
            result.append(new String(filecontent, "utf8"));
        } catch (FileNotFoundException e) {
            log.error("{}", e.getLocalizedMessage());
        } catch (IOException e) {
            log.error("{}", e.getLocalizedMessage());
        }

        String text = result.toString();


        IndexRequest indexRequest = new IndexRequest("article");

        indexRequest.id("2"); //数据id

        Article article = new Article();
        article.setAuthor("ChenHQ");
        article.setContent(text);
        article.setId(2);
        article.setTitle("java文件");
        article.setFileFingerprint("2");
        article.setPath("C:\\Users\\Thompson\\Desktop\\es\\AppletTicketTypeController.java");

        String jsonString = JSON.toJSONString(article);

        indexRequest.source(jsonString, XContentType.JSON);

        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(response);


    }

    /**
     * 上传word文件类型文档
     */
    @Test
    public void uploadWordFile() throws Exception{

        File file = new File("C:\\Users\\Thompson\\Desktop\\es\\222.docx");

        StringBuffer result = new StringBuffer();

        //1、读取文件内容
//        String s = WordUtil.readWORD("C:\\Users\\Thompson\\Desktop\\es\\222.docx");
        String s = WordUtil.readWORD2007("C:\\Users\\Thompson\\Desktop\\es\\222.docx");
        System.out.println("s = " + s);


        String text = result.toString();


       /* IndexRequest indexRequest = new IndexRequest("article");

        indexRequest.id("3"); //数据id

        Article article = new Article();
        article.setAuthor("zhouKang");
        article.setContent(text);
        article.setId(3);
        article.setTitle("word文件");
        article.setFileFingerprint("3");
        article.setPath("C:\\Users\\Thompson\\Desktop\\es\\111.doc");

        String jsonString = JSON.toJSONString(article);

        indexRequest.source(jsonString, XContentType.JSON);

        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(response);*/


    }

    public static void main(String[] args) throws Exception {
        InputStream in = null;
        byte[] data = null;



        URL url = new URL("https://huitailang-hello.oss-cn-beijing.aliyuncs.com/testchen.txt");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

            String file = url.getFile();
            //conn.getContentLength() 获取到读取的数据流大小
            log.info("文件名={},文件长度={}",file,conn.getContentLength());

            in = conn.getInputStream();

            //2、建立数据通道
            BufferedInputStream inputStream = new BufferedInputStream(in);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File("C:\\Users\\Thompson\\Desktop\\es\\123.txt")));
            byte[] buf = new byte[2048];
            int length = 0;
            //循环读取文件内容，输入流中将最多buf.length个字节的数据读入一个buf数组中,返回类型是读取到的字节数。
            //当文件读取到结尾时返回 -1,循环结束。
            while((length = inputStream.read(buf)) != -1){
                System.out.print(new String(buf,0,length));
                outputStream.write(buf,0,length);
            }



            outputStream.close();
            inputStream.close();
            in.close();
            conn.disconnect();
        }


    }


    @Test
    public void testWord() {

        String pathDoc = "C:\\Users\\Thompson\\Desktop\\es\\pic\\";
        String path = "C:\\Users\\Thompson\\Desktop\\es\\1111.doc";

        String content = null;
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            InputStream is = null;
            HWPFDocument doc = null;
            XWPFDocument docx = null;
            POIXMLTextExtractor extractor = null;
            try {
                is = new FileInputStream(file);
                if (path.endsWith(".doc")) {
                    doc = new HWPFDocument(is);

                    // 文档文本内容
                    content = doc.getDocumentText();

                    // 文档图片内容
                    PicturesTable picturesTable = doc.getPicturesTable();
                    List<Picture> pictures = picturesTable.getAllPictures();
                    for (Picture picture : pictures) {
                        // 输出图片到磁盘
                        OutputStream out = new FileOutputStream(
                                new File(pathDoc + UUID.randomUUID() + "." + picture.suggestFileExtension()));
                        picture.writeImageContent(out);
                        out.close();
                    }
                } else if (path.endsWith("docx")) {
                    docx = new XWPFDocument(is);
                    extractor = new XWPFWordExtractor(docx);

                    // 文档文本内容
                    content = extractor.getText();

                    // 文档图片内容
                    List<XWPFPictureData> pictures = docx.getAllPictures();
                    for (XWPFPictureData picture : pictures) {
                        byte[] bytev = picture.getData();
                        // 输出图片到磁盘
                        FileOutputStream out = new FileOutputStream(
                                pathDoc + UUID.randomUUID() + picture.getFileName());
                        out.write(bytev);
                        out.close();
                    }
                } else {
                    System.out.println("此文件不是word文件！");
                }
                System.out.println(content);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                try {
                    if (doc != null) {
                        doc.close();
                    }
                    if (extractor != null) {
                        extractor.close();
                    }
                    if (docx != null) {
                        docx.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                }
            }
        }
    }














}
