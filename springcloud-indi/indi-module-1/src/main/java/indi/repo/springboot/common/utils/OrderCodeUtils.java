package indi.repo.springboot.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 描述信息：随机生成订单编码
 *
 **/
public class OrderCodeUtils {
    /**
     * 随机数code的长度
     */
    private final static int RANDOM_CODE_LENGTH = 4;

    /**
     * 当前时间的长度
     */
    private final static int TIME_CODE_LENGTH = 6;

    /**
     * 导服派单前缀
     */
    public final static String DF = "DF";

    /**
     * 导游编号
     */
    public final static String DY = "DY";

    /**
     * 当前毫秒时间
     */
    private static String timeStr = String.valueOf(System.currentTimeMillis());

    /**
     * 截取毫秒时间6位
     */
    private static String timeCode = timeStr.substring(timeStr.length() - TIME_CODE_LENGTH);

    /**
     * 时间格式
     */
    private static String TIME_PATTERN = "yyyyMMdd";

    /**
     * 随机数初始化
     */
    private static Random random = new Random();

    public static String dataToString() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_PATTERN);
        return dateFormat.format(now);
    }


    /**
     * 功能描述: 获取导服订单的编号
     */
    public static String getDFOrderCode() {
        StringBuilder randomCode = new StringBuilder();
        for (int i = 0; i < RANDOM_CODE_LENGTH; i++) {
            randomCode.append(random.nextInt(10));
        }
        String code = DF + dataToString() + timeCode + randomCode;
        return code;
    }

    /**
     * 功能描述: 获取导游编号
     */
    public static String getDYCode() {
        StringBuilder randomCode = new StringBuilder();
        for (int i = 0; i < RANDOM_CODE_LENGTH; i++) {
            randomCode.append(random.nextInt(10));
        }
        String code = DY + dataToString() + timeCode + randomCode;
        return code;
    }

    public static void main(String[] args) {
        System.out.println(getDFOrderCode());
        System.out.println(getDYCode());
    }


}
