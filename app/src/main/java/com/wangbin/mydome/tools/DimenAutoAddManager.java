package com.wangbin.mydome.tools;

/**
 * @ClassName DimenAutoAddManager
 * @Description TODO
 * @Author WangBin
 * @Date 2019/3/25 11:50
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenjing.liu on 17/4/27.
 * 自动生成dimens.xml文件
 * <p>
 * 只要对main方法里面的调用方法的参数配置下就可以完成自动添加UI工程师给定的dimen尺寸.
 * 具体的还在完善中...2017／05/03
 * 1、支持添加UI标记的尺寸对应'DIMEN'标记
 * 2、支持添加UI标记的字体对应'F'标记
 * 3、尺寸标记的计算规则：(不支持奇数尺寸)
 * 由于UI给的是IOS的UI图，所以该文件转换的规则为 （UI标注的px/2）
 * 每增加一个尺寸需要在sw360dp添加对应的，转换公式为（360/320）x 基础dimens参数，其中360为对应的文件夹的名称
 * 4、字体的计算规则：(不支持>99的字体大小)
 * 默认的/320文件夹下的为 (UI标注值/2 -1)
 * 360/400的为 (UI标注值/2 )
 * 480的为 (UI标注值/2 +1 ) 完工2017/05/04 by wenjing.liu
 */

public class DimenAutoAddManager {
    /**
     * 对应的文件夹的根路径
     */
    private static final String VALUE_ROOT_PATH = "./app/src/main/res/values";
    /**
     * 对应的不同类型的文件夹
     */
    private static final int VALUE = 0;
    private static final int VALUE320 = 320;
    private static final int VALUE360 = 360;
    private static final int VALUE400 = 400;
    private static final int VALUE480 = 480;
    /***
     * 不同的生成name的前缀
     */
    private static final String DIMEN = "dimen"; //对应的UI标记尺寸
    private static final String F = "f"; //对应的字体

    /***
     * 对应的UI设计的标准，是按照640p还是1080p
     */
    private enum Rule {
        UI640P, UI1080P
    }

    private static List<Integer> officialFont = new ArrayList();

    public static void main(String[] args) {
        initOfficialFont();
        for (int i = 28; i < 90; i += 4) {
            autoAddDimensForDifferentValue(i, DIMEN);

        }
        autoAddDimensForDifferentValue(34, F);
        autoAddDimensForDifferentValue(30, F);
        autoAddDimensForDifferentValue(28, F);
        autoAddDimensForDifferentValue(24, F);
    }


    /***
     * 默认 对应生成是values和values-sw360dp下对应的dimens.xml文件
     * @param uiPx UI工程师标记的UI尺寸
     * @param pre 生成dimen的name对应的前缀
     */
    private static void autoAddDimensForDifferentValue(int uiPx, String pre) {
        autoAddDimensForDifferentValue(uiPx, pre, VALUE);
        autoAddDimensForDifferentValue(uiPx, pre, VALUE360);
    }

    /***
     *
     * @param uiPx UI工程师标记的UI尺寸
     * @param pre 生成dimen的name对应的前缀
     * @param value 对应生成是values/values-sw360dp...下对应的dimens.xml文件
     */
    private static void autoAddDimensForDifferentValue(int uiPx, String pre, int value) {
        if (uiPx % 2 != 0) {
            systemOutPrintln("Warning: do not define odd px , you can + 1 or - 1 !", (Object)null);
            return;
        }
        if (uiPx > 99 && F.equals(pre)) {
            systemOutPrintln("Warning: do not define font > 99 !",(Object) null);
            return;
        }
        File file = getXmlFileByValue(value);
        if (file == null) {
            return;
        }

        if (file.length() <= 0) {
            //说明是个空文件，要将xml文件写好
            writeForEmptyDimenXml(uiPx, pre, value, file);
            return;
        }
        //写入一个已经存在的xml文件
        writeForExistDimenXml(uiPx, pre, value, file);
    }


    private static boolean hasPre = false; //是否存在过该前缀
    private static boolean isExistPxBreak = false;//是否需要中断该次循环

    /***
     * 已经有dimens.xml文件了
     * @param uiPx
     * @param pre
     * @param value
     * @param file
     */
    private static void writeForExistDimenXml(int uiPx, String pre, int value, File file) {
        boolean reWrite = false;
        List dimens = new ArrayList();
        BufferedReader reader = null;
        hasPre = false;// 已经存在过对应的pre标签的定义
        isExistPxBreak = false; //是否需要中断该次循环
        try {
            reader = new BufferedReader(new FileReader(file));
            String lineString;
            while ((lineString = reader.readLine()) != null) {

                //写过了就不用在写了！！！！！
                if (isLineEndWithTagNotWrite(lineString, "</dimen>", reWrite)) {
                    reWrite = handleMiddleDimen(uiPx, pre, value, lineString, dimens);
                    if (isExistPxBreak) {
                        isExistPxBreak = false;
                        break;
                    }
                } else if (isLineEndWithTagNotWrite(lineString.trim(), "</resources>", reWrite)) {
                    //已经是最后一个标签了，但是reWrite还是false,说明是最大一个
                    reWrite = handleMaxDimen(uiPx, pre, value, dimens);
                }
                //其他不需要处理的dimen一直都到dimens集合中
                dimens.add(lineString);

            }
            systemOutPrintln("You have finished to read %d dimens .", dimens.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!reWrite)
            return;

        writeDimenXml(uiPx + "px", dimens, file);

    }

    /***
     * 该行是不是以tag结尾
     * @param lineString
     * @param tag
     * @return
     */
    private static boolean isLineEndWithTagNotWrite(String lineString, String tag, boolean isWrited) {
        return lineString.endsWith(tag) && !isWrited;
    }


    /***
     * 该uiPx是中间的大小，要按照从小到大的顺序插入到集合中
     * @param uiPx
     * @param pre
     * @param value
     * @param lineString
     * @param dimens
     * @return true插入成功
     */
    private static boolean handleMiddleDimen(int uiPx, String pre, int value, String lineString, List<String> dimens) {
        boolean isSuccess = false;
        String name = getDimenNameFromAttr(lineString); //截取dimen_20px,即属性名
        int pXing = getUiPxFromAttr(name);
        String existName = getPreFromAttr(name);
        //systemOutPrintln("existName = " + existName + " , pXing = " + pXing, null);
        //如果是字体，要将标准的三位只取后两位
        if (existName.contains(F) && pXing > 99) {
            pXing = pXing % 100; //只取后两位
        }
        if (existName.contains(pre)) {
            hasPre = true;
            //即使重复不添加
            if (uiPx == pXing) {
                systemOutPrintln("Warning: You has already added %dpx .", uiPx);
                isSuccess = false;
                isExistPxBreak = true;

            } else if (uiPx < pXing) {
                // 先加入小的
                dimens.add(getConvertAttrForXml(uiPx, value, pre));
                //该UI像素值恰好为中间像素，转换完之后，在add到list里面
                isSuccess = true;
            }
            return isSuccess;

        }
        if (!hasPre) {
            //没有添加过该前缀,也没有该前缀
            return isSuccess;
        }
        //已经不存在该pre标签了，但是之前已经有过该标签，标示已经是最大的uiPx了，所以仍要累加到最后
        dimens.add(getConvertAttrForXml(uiPx, value, pre));
        isSuccess = true;

        return isSuccess;

    }

    /***
     * 该uiPx是所有中最大的一个尺寸
     * @param uiPx
     * @param pre
     * @param value
     * @param dimens
     * @return
     */
    private static boolean handleMaxDimen(int uiPx, String pre, int value, List<String> dimens) {
        dimens.add(getConvertAttrForXml(uiPx, value, pre));
        return true;
    }

    /***
     * 第一次往dimen.xml文件中写dimens
     * @param uiPx
     * @param pre
     * @param value
     * @return
     */
    private static void writeForEmptyDimenXml(int uiPx, String pre, int value, File file) {
        List<String> dimens = new ArrayList<>();
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        dimens.add(str);
        str = "<resources>\n";
        dimens.add(str);
        str = "  <!-- Default screen margins, per the Android Design guidelines.\n" +
                "    1、尺寸标记的计算规则：\n" +
                "       由于UI给的是IOS的UI图，所以该文件转换的规则为 （UI标注的px/2）\n" +
                "       每增加一个尺寸需要在sw360dp添加对应的，转换公式为（360/320）x 基础dimens参数，其中360为对应的文件夹的名称 \n" +
                "    2、字体的计算规则：\n" +
                "       默认的/320文件夹下的为 (UI标注值/2 -1) \n" +
                "       360的为 (UI标注值/2 )\n" +
                "       480的为 (UI标注值/2 +1 )\n" +
                "    -->\n";
        dimens.add(str);
        str = getConvertAttrForXml(uiPx, value, pre);
        dimens.add(str + "\n");
        str = "</resources>\n";
        dimens.add(str);

        systemOutPrintln("You are lucky , you first write %d size for dimens.xml !", dimens.size());
        writeDimenXml(uiPx + "px", dimens, file);
    }

    /***
     * 初始化UI规定的6种字体值
     */
    private static void initOfficialFont() {
        officialFont.add(48);
        officialFont.add(36);
        officialFont.add(34);
        officialFont.add(30);
        officialFont.add(28);
        officialFont.add(24);

    }

    /***
     * 根据不同的前缀获得对应的字符串
     * @param uiPx
     * @param value
     * @param pre
     * @return
     */
    private static String getConvertAttrForXml(int uiPx, int value, String pre) {
        if (DIMEN.startsWith(pre)) {
            return getConvertDimenForXml(uiPx, value);
        } else if (F.startsWith(pre)) {
            return getConvertFontForXml(uiPx, value);
        }
        return "";
    }

    /***
     * 得到写入xml文件中的定义的dimen
     * @param uiPx UI标记的尺寸
     * @param value 是写入value还是value－swxxx文件中
     * @return
     */
    private static String getConvertDimenForXml(int uiPx, int value) {
        String result = getConvertDimensByRule(uiPx, value);
        String lineString = String.format("    <dimen name=\"%s_%dpx\">%sdp</dimen>", DIMEN, uiPx, result);
        systemOutPrintln("You will add %dpx, to \"%s\"in dimens.xml !", uiPx, lineString);
        return lineString;
    }

    /***
     * 得到对应的UI设计的字体
     * @param uiFont UI标记的尺寸
     * @param value 是写入value还是value－swxxx文件中
     * @return
     */
    private static String getConvertFontForXml(int uiFont, int value) {
        String result = getConvertFontByRule(uiFont, value);
        String addPre = uiFontInOfficialFont(uiFont);
        String lineString = String.format("    <dimen name=\"%s%s_%d\">%ssp</dimen>", F, addPre, uiFont, result);
        systemOutPrintln("You will add %d f, to \"%s\"in dimens.xml !", uiFont, lineString);
        return lineString;
    }

    /***
     * 获得字体对应的前缀
     * @param font
     * @return
     */
    private static String uiFontInOfficialFont(int font) {
        int result = officialFont.indexOf(font) + 1;
        if (result == 0)
            return "";
        return String.format("0%d", result);
    }


    /***
     *获得对应转换的dp值,默认为rule为640p
     * @param uiPx
     * @param value 320 360....
     * @return
     */
    private static String getConvertDimensByRule(int uiPx, int value) {
        return getConvertDimensByRule(uiPx, Rule.UI640P, value);
    }

    /***
     * 获得对应转换的dp值
     * @param uiPx
     * @param rule 640p还是1080p
     * @param value 320 360....
     * @return
     */
    private static String getConvertDimensByRule(int uiPx, Rule rule, int value) {
        double result = 1;
        double multiple = 1;
        float valueTimes = value <= 320 ? 1 : value / 320.0f; //对应不同的文件夹获得的dp


        switch (rule) {
            case UI640P:
                multiple = 1.0 / 2.0 * valueTimes; //2.0 为IOS 640P的时候1:2px
                break;
            case UI1080P:
                multiple = 1.0 / 2.5 * valueTimes;//2.0 为IOS 1080P的时候1:2.5px
                break;
        }
        result = result * multiple * uiPx;
        String back = formatResultByTwoDecimal(result);
        return back;
    }

    /***
     * 根据规则转换对应的字体大小对应值
     * @param uiFont
     * @param value 320 360....
     * @return
     */
    private static String getConvertFontByRule(int uiFont, int value) {
        int font = uiFont / 2;
        switch (value) {
            case VALUE:
            case VALUE320:
                font = font - 1;
                break;
            case VALUE480:
                font = font + 1;
                break;
        }
        return String.valueOf(font);
    }

    /***
     * 保留两位小数
     * @param dimen
     * @return
     */
    private static String formatResultByTwoDecimal(double dimen) {
        DecimalFormat dFormat = new DecimalFormat("0.00");
        String result = dFormat.format(dimen);
        return result;
    }

    /***
     * 写xml文件
     * @param dimenResult
     * @param rewrite
     */
    private static void writeDimenXml(String dimenResult, List<String> rewrite, File file) {
        systemOutPrintln("You will write %s in dimens.xml, all dimens size is %d .", dimenResult, rewrite.size());
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, false));
            for (String str : rewrite)
                writer.write(str + "\n");
            writer.close();
            systemOutPrintln("You write dimens.xml success.", (Object)null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /***
     * 屏幕输出
     * @param format
     * @param args
     */
    private static void systemOutPrintln(boolean isDebug, String format, Object... args) {
        if (isDebug)
            System.out.println(String.format(format, args));
    }

    /***
     * 屏幕输出
     * @param format
     * @param args
     */
    private static void systemOutPrintln(String format, Object... args) {
        systemOutPrintln(true, format, args);// 默认应为true
    }

    /***
     * 获得dimen.xml
     * @param value
     * @return
     */
    private static File getXmlFileByValue(int value) {
        String suffix = value <= 0 ? "/" : "-sw" + value + "dp/";
        String folderPath = VALUE_ROOT_PATH + suffix;
        String path = folderPath + "dimens.xml";
        File file = new File(path);
        if (file.exists())
            return file;
        try {
            File folder = new File(folderPath);
            if (!folder.exists())
                folder.mkdirs();
            boolean isCreate = file.createNewFile();
            if (isCreate)
                return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getDimenNameFromAttr(String lineString) {
        int start = lineString.indexOf("\"");
        int end = lineString.lastIndexOf("\"");
        String name = lineString.substring(start + 1, end); //截取dimen_20px,即属性名
        return name;
    }

    /***
     * 从属性中获取字符串的UI标记尺寸，方便把要加入的尺寸放入到xml文件中
     * @param attr
     * @return
     */
    private static int getUiPxFromAttr(String attr) {
        int result = -1;
        String rule = "[^0-9]";
        Pattern p = Pattern.compile(rule);
        Matcher matcher = p.matcher(attr);
        try {
            result = Integer.parseInt(matcher.replaceAll(""));
        } catch (Exception e) {
        }
        return result;
    }

    /***
     * 获取属性中的前缀，是dimen／f／size
     * @param attr
     * @return
     */
    private static String getPreFromAttr(String attr) {
        String result = null;
        String rule = "[^a-z^A-Z]";
        Pattern p = Pattern.compile(rule);
        Matcher matcher = p.matcher(attr);
        try {
            result = matcher.replaceAll("");
        } catch (Exception e) {
        }
        return result;
    }
}