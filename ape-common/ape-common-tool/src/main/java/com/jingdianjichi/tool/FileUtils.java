package com.jingdianjichi.tool;

import java.io.File;

/**
 * 文件操作相关工具类
 *
 * @author: ChickenWing
 * @date: 2023/1/26
 */
public class FileUtils {

    public static String getCanonicalPath(String pathName) throws Exception {
        File directory = new File(pathName);
        return directory.getCanonicalPath();
    }

}
