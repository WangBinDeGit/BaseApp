package com.wangbin.mydome.tools;


import android.os.Build;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串处理Util类
 *
 * @author pan
 */
public class StringUtil {

    private static final char HALF_SPACE = ' ';
    private static final char FULL_SPACE = '　';


    /**
     * 判断一个字符是否为空格(全角、半角)
     *
     * @param character char
     * @return boolean
     */
    public static boolean isWhitespace(char character) {
        return character == HALF_SPACE || character == FULL_SPACE;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 参数
     * @return boolean true为空
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() <= 0;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 参数
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return isEmpty(((CharSequence) (str)));
    }

    /**
     * 判断字符串是否不为空 <br>
     *
     * @param str 参数
     * @return boolean
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 参数
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否不为空并且不 equals "null"
     *
     * @param str 参数
     * @return boolean
     */
    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    /**
     * 判断字符串是否为空 或者 equals "null"
     *
     * @param str 参数
     * @return boolean
     */
    public static boolean isNull(String str) {
        return isEmpty(str) || "null".equals(str);
    }

    /**
     * 判断字符串是否包含字符（全角空格、半角空格除外）
     *
     * @param str CharSequence
     * @return boolean
     */
    public static boolean hasText(CharSequence str) {
        if (isEmpty(str)) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否包含字符（全角空格、半角空格除外）
     *
     * @param str String
     * @return boolean
     */
    public static boolean hasText(String str) {
        return hasText(((CharSequence) (str)));
    }


    /**
     * 判断字符串是否包含空格（全角、半角）
     *
     * @param str CharSequence
     * @return boolean
     */
    public static boolean containsWhitespace(CharSequence str) {

        if (isEmpty(str)) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否包含空格（全角、半角）
     *
     * @param str String
     * @return boolean
     */
    public static boolean containsWhitespace(String str) {
        return containsWhitespace(((CharSequence) (str)));
    }

    /**
     * 删除字符前面空格（全角、半角）
     *
     * @param str String
     * @return String
     */
    public static String trimLeft(String str) {

        if (isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        for (; sb.length() > 0 && isWhitespace(sb.charAt(0)); sb.deleteCharAt(0)) ;
        return sb.toString();

    }

    /**
     * 删除字符后面空格（全角、半角）
     *
     * @param str String
     * @return String
     */
    public static String trimRight(String str) {
        if (isEmpty(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        for (; sb.length() > 0 && isWhitespace(sb.charAt(sb.length() - 1)); sb.deleteCharAt(sb.length() - 1))
            ;
        return sb.toString();
    }

    /**
     * 删除字符串中头尾空格（全角、半角）
     *
     * @param str String
     * @return String
     */
    public static String trim(String str) {
        return trimRight(trimLeft(str));
    }

    /**
     * 去除字符串中所有空格（全角、半角）
     *
     * @param str String
     * @return String
     */
    public static String trimAll(String str) {
        if (isEmpty(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; sb.length() > i; ) {
            if (isWhitespace(sb.charAt(i))) {
                sb.deleteCharAt(i);
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * 删除字符前面指定字符
     *
     * @param str       String
     * @param character 开始字符
     * @return String
     */
    public static String trimLeft(String str, char character) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        for (; sb.length() > 0 && sb.charAt(0) == character; sb.deleteCharAt(0)) ;
        return sb.toString();
    }

    /**
     * 删除字符后面指定字符
     *
     * @param str       String
     * @param character 结尾字符
     * @return String
     */
    public static String trimRight(String str, char character) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        for (; sb.length() > 0 && sb.charAt(sb.length() - 1) == character; sb.deleteCharAt(sb.length() - 1))
            ;
        return sb.toString();
    }


    /**
     * 删除字符串前后指定字符
     *
     * @param str       String
     * @param character 指定字符
     * @return String
     */
    public static String trimCharacter(String str, char character) {
        return trimRight(trimLeft(str, character), character);
    }

    /**
     * 删除字符串中所有指定字符
     *
     * @param str       string
     * @param character char
     * @return String
     */
    public static String trimAll(String str, char character) {
        if (isEmpty(str)) {
            return str;
        }
//		string本身是不可改变的，它只能赋值一次，每一次内容发生改变，都会生成一个新的对象，然后原有的对象引用新的对象，
//		而每一次生成新对象都会对系统性能产生影响，这会降低.NET编译器的工作效率。
//		而StringBuilder类则不同，每次操作都是对自身对象进行操作，而不是生成新的对象，其所占空间会随着内容的增加而扩充，
//		这样，在做大量的修改操作时，不会因生成大量匿名对象而影响系统性能。
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; sb.length() > i; ) {
            if (sb.charAt(i) == character) {
                sb.deleteCharAt(i);
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否以prefix开始(忽略大小写)
     *
     * @param str    str
     * @param prefix prefix
     * @return boolean
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {

        // 如果源字符串str或者前缀字符串prefix为null，则返回false
        if (str == null || prefix == null) {
            return false;
        }

        // 如果str以prefix开始，return true
        if (str.startsWith(prefix)) {
            return true;
        }

        // 如果源字符串的长度小于prefix的长度，返回false
        if (str.length() < prefix.length()) {
            return false;
        }

        // 将源字符串prefix长度转换为小写，前缀字符串也转换为小写，比较是否相等
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }

    /**
     * 判断字符串是否以suffix结束(忽略大小写)
     *
     * @param str    str
     * @param suffix suffix
     * @return boolean
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if (str == null || suffix == null) {
            return false;
        }

        if (str.endsWith(suffix)) {
            return true;
        }

        if (str.length() < suffix.length()) {
            return false;
        }

        String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
        String lcSuffix = suffix.toLowerCase();
        return lcStr.equals(lcSuffix);

    }

    /**
     * 从左开始截取指定位数字符串
     *
     * @param str String
     * @param len 截取位数
     * @return String
     */
    public static String left(String str, int len) {
        return str.substring(0, len);
    }

    /**
     * 从右侧开始截取指定位数字符串
     *
     * @param str String
     * @param len 截取位数
     * @return String
     */
    public static String right(String str, int len) {
        return str.substring(str.length() - len);
    }


    /**
     * 在某字符串中搜索子字符串第n次出现位置
     *
     * @param str    字符串
     * @param subStr 子字符串
     * @param n      第n次出现位置
     * @param start  开始搜索位置
     * @return int
     */
    public static Integer find(String str, String subStr, int n, int start) {

        if (n < 2) {
            return str.indexOf(subStr, start);
        }

        return find(str, subStr, n - 1, str.indexOf(subStr, start) + 1);
    }

    public static Integer find(String str, String subStr, int n) {
        return find(str, subStr, n, 0);
    }

    public static void deleteList(List<String> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.removeIf(StringUtil::containsWhitespace);
        }
    }

    /**
     * 获取字符串中所有数字
     *
     * @param str 字符串
     * @return String
     */
    public static String getNumerFromStr(String str) {
        if (str != null) str = str.trim();
        StringBuilder str2 = new StringBuilder();
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2.append(str.charAt(i));
                }
            }
        }
        return str2.toString();
    }

    /**
     * 判读字符串中是否只有数字
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[\\+\\-]?\\d*?\\.?\\d*?$");
        return pattern.matcher(str).matches();
    }

    /**
     * 将null转换为"" 防止空指针异常
     *
     * @param str 字符串
     * @return String
     */
    public static String nullToStr(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }
}
