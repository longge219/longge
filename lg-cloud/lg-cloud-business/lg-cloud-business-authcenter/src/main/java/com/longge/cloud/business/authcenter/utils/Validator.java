package com.longge.cloud.business.authcenter.utils;
import java.util.regex.Pattern;
/**
 * @author:jianglong 
 * @description: 校验器：利用正则表达式校验邮箱、手机号等
 * @date: 2019-01-25
 * */
public class Validator {
    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-z0-9A-Z\\u0020-\\u002F\\u003A-\\u0040\\u005B-\\u0060\\u007B-\\u007E]{6,20}$";
    
    /**
     * 正则表达式：验证包含空格
     */
    public static final String RGGEX_HAS_SPACE = ".*\\s+.*";
    
    /**
     * 正则表达式：验证包含数字
     */
    public static final String REGEX_HAS_NUMBER = ".*\\d+.*";
    
    /**
     * 正则表达式：验证数字
     */
    public static final String REGEX_NUMBER = "^[\\d]+$";
    
    /**
     * 正则表达式：验证包含字母
     */
    public static final String REGEX_HAS_LETTER = ".*[a-zA-Z]+.*";
    
    /**
     * 正则表达式：验证字母
     */
    public static final String REGEX_LETTER = "^[a-zA-Z]+$";
    
    /**
     * 正则表达式：验证包含标点符号
     */
    public static final String REGEX_HAS_PUNCTUATION = ".*[\\u0020-\\u002F\\u003A-\\u0040\\u005B-\\u0060\\u007B-\\u007E]+.*";
 
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1[3|5|7|8|9][0-9]\\d{8}$";
    
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z_]@([a-z0-9A-Z_]+(-[a-z0-9A-Z_]+)?\\.)+[a-zA-Z]{2,}$";
 
    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\\u4e00-\\u9fa5],{0,}$";
 
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
 
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
 
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    
    
    /**
     * 校验数字
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isNumber(String value) {
        return Pattern.matches(REGEX_NUMBER, value);
    }
    
    /**
     * 校验字母
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isLetter(String value) {
        return Pattern.matches(REGEX_LETTER, value);
    }
    
    /**
     * 校验密码
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
    
    /**
     * 校验包含空格
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean haveSpace(String password) {
        return Pattern.matches(RGGEX_HAS_SPACE, password);
    }
    
    /**
     * 校验包含数字
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean haveNumber(String password) {
        return Pattern.matches(REGEX_HAS_NUMBER, password);
    }
    
    /**
     * 校验包含字母
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean haveLetter(String password) {
        return Pattern.matches(REGEX_HAS_LETTER, password);
    }
    
    /**
     * 校验包含标点符号
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean havePunctuation(String password) {
        return Pattern.matches(REGEX_HAS_PUNCTUATION, password);
    }
 
    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
 
    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
 
    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }
 
    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
 
    /**
     * 校验URL
     * 
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }
 
    /**
     * 校验IP地址
     * 
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
}