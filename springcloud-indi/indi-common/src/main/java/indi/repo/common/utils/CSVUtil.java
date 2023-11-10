package indi.repo.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;

/**
 * @author Andon
 * 2022/2/18
 */
@Slf4j
public class CSVUtil {

    /**
     * 行尾分隔符定义
     */
    private static final String NEW_LINE_SEPARATOR = System.lineSeparator();

    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.builder().setIgnoreEmptyLines(true).setRecordSeparator(NEW_LINE_SEPARATOR).setQuote(null).build();

    /**
     * 读取CSV文件的内容
     * <p>
     * NOTE: 太大的文件不推荐使用
     *
     * @param filePath 文件路径
     * @param indexArr 参与计算的列的组合角标
     * @return 表内容集合，key是组合ID，value是整行数据
     */
    public static Map<String, String> readCSVToMap(String filePath, String[] indexArr) throws IOException {
        String charset = charset(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            return records(fileInputStream, charset, csvRecords -> {
                Map<String, String> map = new HashMap<>();
                //通过首行获取列数量
                int colNum = csvRecords.get(0).size();
                for (CSVRecord record : csvRecords) {
                    // 每行的内容
                    List<String> value = new ArrayList<>(colNum);
                    for (int i = 0; i < colNum; i++) {
                        value.add(record.get(i));
                    }
                    // 每行ID
                    List<String> key = new ArrayList<>(indexArr.length);
                    for (String index : indexArr) {
                        key.add(record.get(Integer.parseInt(index)));
                    }
                    String id = String.join(",", key);
                    if (!map.containsKey(id)) {
                        map.put(id, String.join(",", value));
                    }
                }
                return map;
            });
        }
    }

    /**
     * 读取CSV文件的内容
     * <p>
     * NOTE: 太大的文件不推荐使用
     *
     * @param filePath 文件路径
     * @param indexArr 参与计算的列的组合角标
     * @return 表内容集合，value是参与计算的列的数据
     */
    public static List<String> readCSVToList(String filePath, String[] indexArr) throws IOException {
        String charset = charset(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            return records(fileInputStream, charset, csvRecords -> {
                List<String> values = new ArrayList<>();
                for (CSVRecord record : csvRecords) {
                    // 每行的内容
                    List<String> value = new ArrayList<>();
                    if (ObjectUtils.isEmpty(indexArr)) {
                        for (String item : record) {
                            value.add(item.trim());
                        }
                    } else {
                        value = new ArrayList<>(indexArr.length);
                        for (String index : indexArr) {
                            value.add(record.get(Integer.parseInt(index)));
                        }
                    }
                    values.add(String.join(",", value));
                }
                return values;
            });
        }
    }

    /**
     * 读取CSV数据条目数
     *
     * @param filePath 文件路径
     * @return 数据条目数
     */
    public static long readDataCount(String filePath) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            log.error("获取文件行数失败 error: {} e: {}", e.getMessage(), e);
        }
        return lines;
    }

    public static <R> R records(InputStream inputStream, String charset, Function<List<CSVRecord>, R> recordsHandler) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

            CSVParser parser = CSV_FORMAT.parse(bufferedReader);
            // 读取文件每行内容
            List<CSVRecord> records = parser.getRecords();
            return recordsHandler.apply(records);
        }
    }

    public static void recordIterator(String filePath, AilandConsumer<Iterator<CSVRecord>> recordIteratorHandler) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            recordIterator(fileInputStream, charset(filePath), recordIteratorHandler);
        }
    }

    public static void recordIterator(InputStream inputStream, String charset, AilandConsumer<Iterator<CSVRecord>> recordIteratorHandler) throws Exception {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
            CSVParser parser = CSV_FORMAT.parse(bufferedReader);
            recordIteratorHandler.accept(parser.iterator());
        }
    }

    /**
     * 创建CSV文件
     *
     * @param file   File
     * @param head   表头
     * @param values 表体
     */
    public static File makeTempCSV(File file, String[] head, List<String[]> values) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
            CSVPrinter printer = new CSVPrinter(bufferedWriter, CSV_FORMAT);
            // 写入表头
            printer.printRecord(Arrays.asList(head));
            // 写入内容
            for (String[] value : values) {
                printer.printRecord(Arrays.asList(value));
            }
            printer.close();
        }
        return file;
    }

    /**
     * 下载文件
     *
     * @param response HttpServletResponse
     * @param file     File
     */
    public static boolean downloadFile(HttpServletResponse response, File file, String fileName) {
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        OutputStream os = null;
        try {
            // 设置csv文件下载头信息
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            os = response.getOutputStream();
            //MS产本头部需要插入BOM
            //如果不写入这几个字节，会导致用Excel打开时，中文显示乱码
            os.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            byte[] buffer = new byte[1024];
            int i = bufferedInputStream.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bufferedInputStream.read(buffer);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            boolean delete = file.delete();
        }
        return false;
    }

    /**
     * 判断文件字符编码
     */
    public static String charset(String path) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                bis.close();
                return charset; // 文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; // 文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; // 文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; // 文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 > read || read > 0xBF) {
                            break; // 双字节 (0xC0 - 0xDF),(0x80 - 0xBF),也可能在GB编码内
                        }
                    } else if (0xE0 <= read) { // 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                            }
                        }
                        break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("--文件-> [{}] 采用的字符集为: [{}]", path, charset);
        return charset;
    }

    public static String concatCsvSuffix(String fileName) {
        if (!StringUtils.hasText(fileName)) return fileName;
        if (fileName.endsWith(".json") || fileName.endsWith(".csv")) return fileName;
        return fileName + ".csv";
    }

    public static void main(String[] args) throws Exception {

        CSVUtil.recordIterator("/Users/admin/Downloads/read.csv", csvRecordIterator -> {
            List<String> values = new ArrayList<>();
            while (csvRecordIterator.hasNext()) {
                CSVRecord next = csvRecordIterator.next();
                System.out.println("next = " + next);
                values.add(String.join(",", next.toList()));
            }
            System.out.println("values = " + values);
        });

    }


}
