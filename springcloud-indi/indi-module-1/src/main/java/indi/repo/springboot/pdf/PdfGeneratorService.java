//package indi.repo.springboot.pdf;
//
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
//import org.xhtmlrenderer.pdf.ITextFontResolver;
//import org.xhtmlrenderer.pdf.ITextRenderer;
//
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.pdf.BaseFont;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class PdfGeneratorService {
//
//    // 模板引擎：Thymeleaf
//    private TemplateEngine templateEngine;
//
//    public PdfGeneratorService() {
//        // 初始化 Thymeleaf 模板解析器（从 classpath 加载模板）
//        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setPrefix("templates/"); // 模板文件所在目录（resources/templates/）
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode("HTML");
//        resolver.setCharacterEncoding("UTF-8");
//
//        templateEngine = new TemplateEngine();
//        templateEngine.setTemplateResolver(resolver);
//    }
//
//    // 生成 PDF 并保存到文件
//    public void generatePdf(String outputPath) throws IOException, DocumentException {
//        // 1. 准备动态数据（后端“默认已有”的指标数据）
//        Context context = new Context();
//        context.setVariable("title", "测试合约");
//        context.setVariable("tradeNo", "99999999");
//        context.setVariable("initiator", "jbr2");
//        context.setVariable("timeRange", "2025-09-16 09:50 ~ 长期有效");
//        context.setVariable("participantCount", "1 方");
//        context.setVariable("tradeDuration", "进行中");
//        context.setVariable("sourceDataSize", "12.99 MB");
//        context.setVariable("resultDataSize", "0 B");
//
//        // 数据共享环节的列表数据
//        List<Map<String, String>> dataItems = new ArrayList<>();
//        Map<String, String> item = new HashMap<>();
//        item.put("datasetName", "密文计算产品");
//        item.put("datasetType", "FILE");
//        item.put("uploader", "jbr1");
//        item.put("uploadIp", "");
//        item.put("datasetSize", "12.99MB");
//        item.put("createTime", "2025-09-16 09:30");
//        dataItems.add(item);
//
//        // 数据共享环节的列表数据
//        Map<String, String> item2 = new HashMap<>();
//        item2.put("datasetName", "密文计算产品");
//        item2.put("datasetType", "FILE");
//        item2.put("uploader", "jbr1");
//        item2.put("uploadIp", "");
//        item2.put("datasetSize", "21MB");
//        item2.put("createTime", "2025-09-16 09:30");
//        dataItems.add(item2);
//        context.setVariable("dataItems", dataItems);
//
//        // 2. 渲染 HTML 模板
//        String htmlContent = templateEngine.process("contract", context);
//
//        // 3. HTML → PDF 转换（使用 Flying Saucer）
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ITextRenderer renderer = new ITextRenderer();
//
//        // 处理中文（若模板包含中文，需加载中文字体）
//         ITextFontResolver fontResolver = renderer.getFontResolver();
//        // 示例：加载系统中文字体（如 Windows 的“宋体”）
//        // 若为 Linux，需确保字体文件存在，或打包字体到项目中
//         fontResolver.addFont("/Users/admin/Downloads/bb2360/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//
//        renderer.setDocumentFromString(htmlContent);
//        renderer.layout();
//        renderer.createPDF(out);
//
//        // 4. 保存 PDF 到文件
//        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
//            out.writeTo(fos);
//        }
//        out.close();
//    }
//
//    // 测试方法
//    public static void main(String[] args) {
//        try {
//            PdfGeneratorService service = new PdfGeneratorService();
//            service.generatePdf("contract.pdf");
//            System.out.println("PDF 生成成功：contract.pdf");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
