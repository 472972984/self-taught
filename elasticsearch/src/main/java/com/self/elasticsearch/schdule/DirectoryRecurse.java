package com.self.elasticsearch.schdule;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/25
 * @desc:
 */

//import com.alibaba.fastjson.JSONObject;
//import com.self.elasticsearch.model.Article;
//import io.searchbox.client.JestClient;
//import io.searchbox.core.Index;
//import io.searchbox.core.Search;
//import io.searchbox.core.SearchResult;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jmimemagic.*;
//import org.apache.poi.hwpf.extractor.WordExtractor;
//import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;

//@Component
//@Slf4j
//public class DirectoryRecurse {
//
////    @Autowired
//    private JestClient jestClient;
//
//    //读取文件内容转换为字符串
//    private String readToString(File file, String fileType) {
//        StringBuffer result = new StringBuffer();
//        switch (fileType) {
//            case "text/plain":
//            case "java":
//            case "c":
//            case "cpp":
//            case "txt":
//                try (FileInputStream in = new FileInputStream(file)) {
//                    Long filelength = file.length();
//                    byte[] filecontent = new byte[filelength.intValue()];
//                    in.read(filecontent);
//                    result.append(new String(filecontent, "utf8"));
//                } catch (FileNotFoundException e) {
//                    log.error("{}", e.getLocalizedMessage());
//                } catch (IOException e) {
//                    log.error("{}", e.getLocalizedMessage());
//                }
//                break;
//            case "doc":
//                //使用HWPF组件中WordExtractor类从Word文档中提取文本或段落
//                try (FileInputStream in = new FileInputStream(file)) {
//                    WordExtractor extractor = new WordExtractor(in);
////                    result.append(extractor.getText());
//                } catch (Exception e) {
//                    log.error("{}", e.getLocalizedMessage());
//                }
//                break;
//            case "docx":
//                try (FileInputStream in = new FileInputStream(file); XWPFDocument doc = new XWPFDocument(in)) {
//                    XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
//                    result.append(extractor.getText());
//                } catch (Exception e) {
//                    log.error("{}", e.getLocalizedMessage());
//                }
//                break;
//        }
//        return result.toString();
//    }
//
//    //判断是否已经索引
//    private JSONObject isIndex(File file) {
//        JSONObject result = new JSONObject();
//        //用MD5生成文件指纹,搜索该指纹是否已经索引
//        String fileFingerprint = Md5CaculateUtil.getMD5(file);
//        result.put("fileFingerprint", fileFingerprint);
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.termQuery("fileFingerprint", fileFingerprint));
//        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("diskfile").addType("files").build();
//        try {
//            //执行
//            SearchResult searchResult = jestClient.execute(search);
//            if (searchResult.getTotal() > 0) {
//                result.put("isIndex", true);
//            } else {
//                result.put("isIndex", false);
//            }
//        } catch (IOException e) {
//            log.error("{}", e.getLocalizedMessage());
//        }
//        return result;
//    }
//
//    //对文件目录及内容创建索引
//    private void createIndex(File file, String method) {
//        //忽略掉临时文件，以~$起始的文件名
//        if (file.getName().startsWith("~$")) return;
//
//        String fileType = null;
//        switch (method) {
//            case "magic":
//                Magic parser = new Magic();
//                try {
//                    MagicMatch match = parser.getMagicMatch(file, false);
//                    fileType = match.getMimeType();
//                } catch (MagicParseException e) {
//                    //log.error("{}",e.getLocalizedMessage());
//                } catch (MagicMatchNotFoundException e) {
//                    //log.error("{}",e.getLocalizedMessage());
//                } catch (MagicException e) {
//                    //log.error("{}",e.getLocalizedMessage());
//                }
//                break;
//            case "ext":
//                String filename = file.getName();
//                String[] strArray = filename.split("\\.");
//                int suffixIndex = strArray.length - 1;
//                fileType = strArray[suffixIndex];
//        }
//
//        switch (fileType) {
//            case "text/plain":
//            case "java":
//            case "c":
//            case "cpp":
//            case "txt":
//            case "doc":
//            case "docx":
//                JSONObject isIndexResult = isIndex(file);
//                log.info("文件名：{}，文件类型：{}，MD5：{}，建立索引：{}", file.getPath(), fileType, isIndexResult.getString("fileFingerprint"), isIndexResult.getBoolean("isIndex"));
//
//                if (isIndexResult.getBoolean("isIndex")) break;
//                //1\. 给ES中索引(保存)一个文档
//                Article article = new Article();
//                article.setTitle(file.getName());
//                article.setAuthor(file.getParent());
//                article.setPath(file.getPath());
//                article.setContent(readToString(file, fileType));
//                article.setFileFingerprint(isIndexResult.getString("fileFingerprint"));
//                //2\. 构建一个索引
//                Index index = new Index.Builder(article).index("diskfile").type("files").build();
//                try {
//                    //3\. 执行
//                    if (!jestClient.execute(index).getId().isEmpty()) {
//                        log.info("构建索引成功！");
//                    }
//                } catch (IOException e) {
//                    log.error("{}", e.getLocalizedMessage());
//                }
//                break;
//        }
//    }
//
//    public void find(String pathName) throws IOException {
//        //获取pathName的File对象
//        File dirFile = new File(pathName);
//
//        //判断该文件或目录是否存在，不存在时在控制台输出提醒
//        if (!dirFile.exists()) {
//            log.info("do not exit");
//            return;
//        }
//
//        //判断如果不是一个目录，就判断是不是一个文件，时文件则输出文件路径
//        if (!dirFile.isDirectory()) {
//            if (dirFile.isFile()) {
//                createIndex(dirFile, "ext");
//            }
//            return;
//        }
//
//        //获取此目录下的所有文件名与目录名
//        String[] fileList = dirFile.list();
//
//        for (int i = 0; i < fileList.length; i++) {
//            //遍历文件目录
//            String string = fileList[i];
//            File file = new File(dirFile.getPath(), string);
//            //如果是一个目录，输出目录名后，进行递归
//            if (file.isDirectory()) {
//                //递归
//                find(file.getCanonicalPath());
//            } else {
//                createIndex(file, "ext");
//            }
//        }
//    }
//}
//
