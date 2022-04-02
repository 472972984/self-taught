package indi.repo.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/7/27
 */
@Slf4j
public class FileExportUtils {

    /**
     * 列表数据导出 Excel
     *
     * @param response      响应对象
     * @param list          待导出集合
     * @param fileName      文件名称
     * @param exportVoClass 对应vo类
     */
    public static void downLoadReport(HttpServletResponse response, List list, String fileName, Class exportVoClass) {
        try {
            //添加响应头信息
            setResponseHead(response, fileName);
            ExcelWriter writer;
            OutputStream outputStream = response.getOutputStream();

            //实例化 ExcelWriter
            writer = EasyExcel.write(outputStream, exportVoClass).build();

            //实例化表单
            WriteSheet sheet = EasyExcel.writerSheet(fileName).build();
            //输出
            writer.write(list, sheet);
            writer.finish();
            outputStream.flush();
        } catch (Exception e) {
            log.error("downLoadReport  service error e:{}", e.getMessage());
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                log.error("downLoadReport  service error e:{}", e.getMessage());
            }
        }
    }

    /**
     * 设置响应头信息
     */
    private static void setResponseHead(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }


}
