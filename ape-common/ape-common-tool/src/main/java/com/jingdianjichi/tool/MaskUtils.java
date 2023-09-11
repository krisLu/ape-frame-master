package com.jingdianjichi.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MaskUtils {

    public static String maskMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        int length = mobile.length();
        char[] mobileChars = mobile.toCharArray();
        char[] resultChars = new char[mobile.length()];
        for (int i = 0; i < length; i++) {
            if (i >= 3 && i < length - 3) {
                resultChars[i] = '*';
            } else {
                resultChars[i] = mobileChars[i];
            }
        }
        return new String(resultChars);
    }

    public static String maskEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return null;
        }
        int length = email.length();
        char[] emailChars = email.toCharArray();
        char[] resultChars = new char[email.length()];
        int atIndex = email.indexOf('@');
        for (int i = 0; i < length; i++) {
            if (i > 0 && i < atIndex - 1) {
                resultChars[i] = '*';
            } else {
                resultChars[i] = emailChars[i];
            }
        }
        return new String(resultChars);
    }

    /**
     * 过滤xml标签
     */
    public static String getContext(String html) {
        String result = html;
        if (StringUtils.isBlank(html)) {
            return null;
        }
        Pattern p = Pattern.compile(">([^</]+)</");
        Matcher m = p.matcher(html);
        if (m.find()) {
            result = m.group(1);
        }
        return result;
    }


    /**
     * 昵称掩码
     */
    public static String maskNickName(String nickName) {
        if (StringUtils.isBlank(nickName)) {
            return "";
        }
        int length = nickName.length();
        char[] nickNameChars = nickName.toCharArray();
        char[] resultChars = new char[nickName.length()];
        for (int i = 0; i < length; i++) {
            if (i > 0 && i < length - 1) {
                resultChars[i] = '*';
            } else if (i == 1 && length == 2) {
                resultChars[i] = '*';
            } else {
                resultChars[i] = nickNameChars[i];
            }
        }
        return new String(resultChars);
    }

}
