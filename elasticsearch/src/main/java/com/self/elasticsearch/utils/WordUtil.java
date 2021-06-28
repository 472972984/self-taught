package com.self.elasticsearch.utils;


import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/28
 * @desc:
 */
public class WordUtil {

    /**
     * 读取doc文件
     * @param file
     * @return
     * @throws Exception
     */
    public static String readWORD(String file) throws Exception {
        String returnStr = "";
        try {
            WordExtractor wordExtractor = new WordExtractor(new FileInputStream(new File(file)));
            returnStr = wordExtractor.getText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * 读取docx文件
     * @param file
     * @return
     * @throws Exception
     */
    public static String readWORD2007(String file) throws Exception {
        OPCPackage opcPackage = POIXMLDocument.openPackage(file);

        POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
        String buffer = extractor.getText();
        extractor.close();
        return buffer;
    }

    /**
     *
     * @Title: getPdfFileText
     * @Description: 获取指定位置pdf的文件内容
     * @param @param fileName
     * @param @return
     * @param @throws IOException
     * @return String 返回类型
     * @throws
     */
    public static String getPdfFileText(String fileName) throws IOException {
        PdfReader reader = new PdfReader(fileName);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        StringBuffer buff = new StringBuffer();
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = parser.processContent(i,
                    new SimpleTextExtractionStrategy());
            buff.append(strategy.getResultantText());
        }
        return buff.toString();
    }


}