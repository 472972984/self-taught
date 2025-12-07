package indi.repo.springboot.toexcel;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenHQ
 * @date 2025/10/30 16:43
 */
public class JsonToExcel {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    // 用于收集所有key-value
    private static final List<KeyValue> keyValueList = new ArrayList<>();

    public static void main(String[] args) {
        // 示例JSON（可替换为你的JSON字符串或Java对象）

        JSON jsonObj = JSONUtil.readJSON(new File("/Users/admin/Documents/work/工作记录/json-cat.txt"), StandardCharsets.UTF_8);

        String jsonStr =  jsonObj.toString();

        try {
            // 1. 解析JSON并收集key-value
            JsonNode rootNode = objectMapper.readTree(jsonStr);
            collectKeyValue(rootNode, "");

            // 2. 将收集到的数据写入Excel
            writeToExcel("/Users/admin/Downloads/json_key_value.xlsx");

            System.out.println("Excel生成成功！");
        } catch (JsonProcessingException e) {
            System.out.println("JSON解析失败：" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Excel写入失败：" + e.getMessage());
        }
    }


    /**
     * 递归收集键值对，支持解析转义的JSON字符串
     */
    private static void collectKeyValue(JsonNode node, String parentKey) {
        if (node.isObject()) {
            // 处理JSON对象
            node.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                String currentKey = parentKey.isEmpty() ? key : parentKey + "." + key;
                collectKeyValue(value, currentKey);
            });
        } else if (node.isArray()) {
            // 处理数组：遍历每个元素，用[索引]标记
            for (int i = 0; i < node.size(); i++) {
                JsonNode element = node.get(i);
                String currentKey = parentKey + "[" + i + "]"; // 拼接索引，如"hobbies[0]"
                collectKeyValue(element, currentKey);
                if (i > 20) {
                    break;
                }
            }
        } else if (node.isTextual()) {
            // 处理字符串：先尝试解析为JSON，若成功则递归，否则按普通字符串处理
            String strValue = node.asText();
            try {
                // 尝试将字符串解析为JSON
                JsonNode parsedNode = objectMapper.readTree(strValue);
                // 解析成功，递归处理内部结构（键路径继承当前parentKey）
                collectKeyValue(parsedNode, parentKey);
            } catch (JsonProcessingException e) {
                // 解析失败，视为普通字符串
                keyValueList.add(new KeyValue(parentKey, strValue));
            }
        } else if (node.isArray()) {
            // 处理数组（直接保存为JSON数组格式）
            keyValueList.add(new KeyValue(parentKey, node.toString()));
        } else {
            // 处理其他类型（数字、布尔、null等）
            keyValueList.add(new KeyValue(parentKey, node.toString()));
        }
    }

    /**
     * 将key-value列表写入Excel文件
     * @param filePath Excel保存路径（如"d:/test.xlsx"）
     */
    private static void writeToExcel(String filePath) throws IOException {
        // 1. 创建工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("JSON键值对");

        // 2. 创建表头（第一行）
        Row headerRow = sheet.createRow(0);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("Key");
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Value");

        // 3. 设置表头样式（可选，优化显示）
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerCell1.setCellStyle(headerStyle);
        headerCell2.setCellStyle(headerStyle);

        // 4. 写入数据行
        for (int i = 0; i < keyValueList.size(); i++) {
            KeyValue keyValue = keyValueList.get(i);
             Row dataRow = sheet.createRow(i + 1); // 从第二行开始写数据
            // 写入Key列
            dataRow.createCell(0).setCellValue(keyValue.getKey());
            // 写入Value列
            try {
                 dataRow.createCell(1).setCellValue(keyValue.getValue());
            } catch (Exception e) {
                System.out.println(keyValue.getKey() + " 单元格文本过长: " + keyValue.getValue().length());
            }
        }

        // 5. 自动调整列宽（优化显示）
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        // 6. 保存Excel文件
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }

        // 7. 关闭资源
        workbook.close();
    }
}