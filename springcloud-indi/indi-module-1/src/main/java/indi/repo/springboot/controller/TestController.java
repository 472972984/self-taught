package indi.repo.springboot.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import indi.repo.springboot.module.dto.StudentDTO;
import indi.repo.springboot.toexcel.DynamicExcelExporter;
import indi.repo.springboot.toexcel.mt.MtRecommendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 */
@RestController
@RequestMapping("")
@RefreshScope
@Slf4j
public class TestController {

    public static void main(String[] args) throws JsonProcessingException {

        // /v1/poi/sputag/products
        JSON json = JSONUtil.readJSON(new File("/Users/admin/Downloads/a.json"), Charset.defaultCharset());
        MtRecommendResponse bean = json.toBean(MtRecommendResponse.class);
         System.out.println("bean = " + bean);
        MtRecommendResponse.Product product = bean.getSpus().get(0);
        System.out.println(mapper.writeValueAsString(product));

//        File file = new File("/Users/admin/Downloads/1122.xlsx");
//        MtRecommendExcelResponse bean = json.toBean(MtRecommendExcelResponse.class);
//        DynamicExcelExporter.exportComplexData(bean.getSpus(), file);

    }

    private static boolean validToken() {
        Long expireTime = 1763110525L;
        if (expireTime.toString().length() == 13) {
            expireTime = expireTime / 1000;
        }
        return expireTime > System.currentTimeMillis() / 1000L;
    }

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        // 序列化配置
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 反序列化配置
        // mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @GetMapping("/toExcel")
    public String toExcel(HttpServletResponse response) throws IOException {
        List<StudentDTO> list = new ArrayList<>();

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setUsername("chq1");
        studentDTO.setSex("M");
        studentDTO.setId(0L);
        studentDTO.setAge(Arrays.asList(1,2,3));
        List<StudentDTO.Sport> sports = new ArrayList<>();
        StudentDTO.Sport sport = new StudentDTO.Sport();
        sport.setSize("100");
        sport.setBall("basketball");
        StudentDTO.Sport sport1 = new StudentDTO.Sport();
        sport1.setSize("80");
        sport1.setBall("football");

        sports.add(sport);
        sports.add(sport1);

        studentDTO.setSports(sports);


        StudentDTO studentDTO2 = new StudentDTO();
        studentDTO2.setUsername("chq2");
        studentDTO2.setSex("F");
        studentDTO2.setId(1L);
        studentDTO2.setAge(Arrays.asList(1,2,3));
        List<StudentDTO.Sport> sports2 = new ArrayList<>();
        StudentDTO.Sport sport3 = new StudentDTO.Sport();
        sport3.setSize("100");
        sport3.setBall("basketball");
        StudentDTO.Sport sport4 = new StudentDTO.Sport();
        sport4.setSize("80");
        sport4.setBall("football");

        sports2.add(sport3);
        sports2.add(sport4);

        studentDTO2.setSports(sports2);

        list.add(studentDTO);
        list.add(studentDTO2);

        // FileExportUtils.downLoadReport(response, list, "11.xlsx", StudentDTO.class);

        DynamicExcelExporter.exportComplexData(list, response, "复杂用户数据");

        return "success";
    }


}
