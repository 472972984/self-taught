package indi.repo.springboot.toexcel;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ChenHQ
 * @date 2025/11/3 16:38
 */
public class DynamicExcelExporter {

    public static void exportComplexData(List<?> dataList, HttpServletResponse response,
                                         String fileName) throws IOException {
        // 展开数据
        List<Map<String, Object>> flatData = ExcelExpandUtil.expandObjects(dataList);

        if (flatData.isEmpty()) {
            return;
        }

        // 动态生成表头
        Set<String> headers = flatData.stream()
                .flatMap(map -> map.keySet().stream())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream())
                .head(generateHead(headers))
                .sheet("数据导出")
                .doWrite(convertData(flatData, headers));
    }

    public static void exportComplexData(List<?> dataList,
                                         File file) {
        // 展开数据
        List<Map<String, Object>> flatData = ExcelExpandUtil.expandObjects(dataList);

        if (flatData.isEmpty()) {
            return;
        }

        // 动态生成表头
        Set<String> headers = flatData.stream()
                .flatMap(map -> map.keySet().stream())
                .collect(Collectors.toCollection(LinkedHashSet::new));


        EasyExcel.write(file)
                .head(generateHead(headers))
                .sheet("数据导出")
                .doWrite(convertData(flatData, headers));
    }

    private static List<List<String>> generateHead(Set<String> headers) {
        return headers.stream()
                .map(header -> Collections.singletonList(header))
                .collect(Collectors.toList());
    }

    private static List<List<Object>> convertData(List<Map<String, Object>> flatData,
                                                  Set<String> headers) {
        List<List<Object>> result = new ArrayList<>();

        for (Map<String, Object> map : flatData) {
            List<Object> row = new ArrayList<>();
            for (String header : headers) {
                row.add(map.getOrDefault(header, ""));
            }
            result.add(row);
        }

        return result;
    }
}