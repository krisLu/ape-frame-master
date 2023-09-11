package com.jingdianjichi.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: ChickenWing
 * @Description: 版本号对比
 * @DateTime: 2022/12/10 21:40
 */
@Slf4j
public class VersionUtils {

    /**
     * 客户端版本 < 目标版本
     */
    public static boolean LT(String clientVersion, String targetVersion) {
        return compareVersion(targetVersion, clientVersion);
    }

    /**
     * 客户端版本 > 目标版本
     */
    public static boolean GT(String clientVersion, String targetVersion) {
        return compareVersion(clientVersion, targetVersion);
    }

    /**
     * 客户端版本 <= 目标版本
     */
    public static boolean LE(String clientVersion, String targetVersion) {
        return StringUtils.equals(clientVersion, targetVersion) || LT(clientVersion, targetVersion);
    }

    /**
     * 客户端版本 >= 目标版本
     */
    public static boolean GE(String clientVersion, String targetVersion) {
        return StringUtils.equals(clientVersion, targetVersion) || GT(clientVersion, targetVersion);
    }

    /**
     * 版本号比较
     */
    public static boolean compareVersion(String newVersion, String clientVersion) {
        Boolean result = false;
        try {
            if (StringUtils.isBlank(newVersion) || StringUtils.isBlank(clientVersion)) {
                return false;
            }
            String[] v1s = newVersion.split("\\.");
            String[] v2s = clientVersion.split("\\.");
            int mainVersion1 = Integer.parseInt(v1s[0]);
            int mainVersion2 = Integer.parseInt(v2s[0]);
            if (mainVersion1 == mainVersion2) {
                int subVersion1 = Integer.parseInt(v1s[1]);
                int subVersion2 = Integer.parseInt(v2s[1]);
                if (subVersion1 == subVersion2) {
                    int buildVersion1 = Integer.parseInt(v1s[2]);
                    int buildVersion2 = Integer.parseInt(v2s[2]);
                    return buildVersion1 > buildVersion2;
                } else {
                    result = subVersion1 > subVersion2;
                }
            } else {
                result = mainVersion1 > mainVersion2;
            }
        } catch (Exception e) {
            log.error("VersionUtils.compareVersion.error", e.getMessage(), e);
        }
        return result;
    }

}
