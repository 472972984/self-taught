package com.self.elasticsearch.schdule;

import java.io.File;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/25
 * @desc:
 */
public class Md5CaculateUtil {


    public static String getMD5(File file) {

        String name = file.getName();
        String path = file.getPath();
        return path + "--" + name;

    }


}
