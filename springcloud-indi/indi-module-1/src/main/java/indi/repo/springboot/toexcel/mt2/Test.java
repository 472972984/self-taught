package indi.repo.springboot.toexcel.mt2;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import indi.repo.springboot.toexcel.DynamicExcelExporter;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ChenHQ
 * @date 2025/12/7 13:11
 */
public class Test {

    public static void main(String[] args) throws JsonProcessingException {

//        JSON jsonDefault = JSONUtil.readJSON(new File("/Users/admin/Documents/workspace/self-taught/springcloud-indi/indi-module-1/src/main/java/indi/repo/springboot/toexcel/mt2/1.json"), Charset.defaultCharset());
//        JSON jsonDefault = JSONUtil.readJSON(new File("/Users/admin/Downloads/test-proxy/sample-5738"), Charset.defaultCharset());

        List<ProductResponse.ProductSpu> productSpuList = new ArrayList<>();
        File file = new File("/Users/admin/Downloads/test-proxy");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            Arrays.stream(files).forEach(file1 -> {
                String name = file1.getName();
                if (!name.startsWith("sample")) {
                    return;
                }
                JSON jsonDefault = JSONUtil.readJSON(file1, Charset.defaultCharset());
                ProductResponse bean = jsonDefault.toBean(ProductResponse.class);
                productSpuList.addAll(bean.getData().getProduct_spu_list());
            });
        }

        System.out.println(productSpuList.size());
        // ProductResponse bean = jsonDefault.toBean(ProductResponse.class);
        // List<ProductResponse.ProductSpu> productSpuList = bean.getData().getProduct_spu_list();


        String filePath = "/Users/admin/Downloads/1207.xlsx";
        File fileFinal = new File(filePath);
        DynamicExcelExporter.exportComplexData(productSpuList, fileFinal);

    }


}
