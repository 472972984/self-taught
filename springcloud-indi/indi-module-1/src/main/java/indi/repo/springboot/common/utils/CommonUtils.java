package indi.repo.springboot.common.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author ChenHQ
 * @date: create in 2021/7/11
 */
@Slf4j
public class CommonUtils {

    public static Map<Integer, Integer> levelScoreMap = Maps.newHashMap();

    static {
        /**
         * 导游星级 0 - 20  1星    20 - 40  2星 	40 - 60  3星   60 - 80  4星  80 - 100上  5星
         */
        levelScoreMap.put(1, 10);
        levelScoreMap.put(2, 30);
        levelScoreMap.put(3, 50);
        levelScoreMap.put(4, 70);
        levelScoreMap.put(5, 90);
    }


    /**
     * ,间隔
     */
    private static final String REGEX = ",";

    /**
     * 集合字符串使用 ,间隔
     *
     * @param strList
     * @return
     */
    public static String getStringWithRegex(List<String> strList) {
        if (CollectionUtils.isEmpty(strList)) {
            return "";
        }
        if (strList.size() == 1) {
            return strList.get(0);
        } else {
            StringBuffer sb = new StringBuffer();
            strList.forEach(s -> {
                sb.append(s).append(REGEX);
            });
            return sb.substring(0, sb.lastIndexOf(REGEX));
        }
    }

    /**
     * ,间隔 还原集合对象
     *
     * @param tempStr
     * @return
     */
    public static List<Long> getListWithRegexLong(String tempStr) {
        if (Objects.isNull(tempStr)) {
            return Collections.EMPTY_LIST;
        }
        if (tempStr.contains(REGEX)) {
            String[] split = tempStr.split(REGEX);
            return Arrays.stream(split).map(s -> Long.parseLong(s)).collect(Collectors.toList());
        } else {
            return Arrays.asList(Long.parseLong(tempStr));
        }
    }


    /**
     * ,间隔 还原集合对象
     *
     * @param tempStr
     * @return
     */
    public static List<String> getListWithRegex(String tempStr) {
        if (Objects.isNull(tempStr)) {
            return Collections.EMPTY_LIST;
        }
        if (tempStr.contains(REGEX)) {
            String[] split = tempStr.split(REGEX);
            return Arrays.stream(split).collect(Collectors.toList());
        } else {
            return Arrays.asList(tempStr);
        }
    }

    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }


    /**
     * 判断是否存在null值字段
     *
     * @param o
     * @return
     */
    public static boolean existNullField(Object o) {
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(o) == null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                log.error("existNullField : {}", e);
            }
        }
        return false;
    }

    /**
     * 根据星级，返回对应的中间分
     *
     * @param level
     * @return
     */
    public static Integer starLevel2Score(Integer level) {
        return levelScoreMap.get(level);
    }

    /**
     * 根据分数，返回对应的星级
     *
     * @param score
     * @return
     */
    public static Integer score2starLevel(Integer score) {
        if (score <= 20 && score > 0) {
            return 1;
        } else if (score <= 40 && score > 20) {
            return 2;
        } else if (score <= 60 && score > 40) {
            return 3;
        } else if (score <= 80 && score > 60) {
            return 4;
        } else if (score > 80) {
            return 5;
        }
        return 1;
    }

    /**
     * 根据星级  返回 星级所在区间
     *
     * @param level
     * @return
     */
    public static List<Integer> getRangeWithLevel(Integer level) {
        if (1 == level) {
            return Arrays.asList(-999, 20);
        } else if (2 == level) {
            return Arrays.asList(20, 40);
        } else if (3 == level) {
            return Arrays.asList(40, 60);
        } else if (4 == level) {
            return Arrays.asList(60, 80);
        } else if (5 == level) {
            return Arrays.asList(80, 999);
        }
        return Arrays.asList(-999, 999);
    }

    /**
     * 根据评价星级 获取 浮动分数
     * 规则：1星-扣5分  2星-扣3分 3星-加1分 4星-加3分 5星-加5分
     * @param level
     * @return
     */
    public static Integer evaluateGuideStar(Integer level) {
        if (1 == level) {
            return -5;
        } else if (2 == level) {
            return -3;
        } else if (3 == level) {
            return 1;
        } else if (4 == level) {
            return 3;
        } else if (5 == level) {
            return 5;
        }
        return 0;
    }


}
