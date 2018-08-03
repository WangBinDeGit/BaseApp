@file:Suppress("NAME_SHADOWING")

package com.wangbin.mydome.tools

import android.annotation.SuppressLint
import java.math.BigDecimal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by WangBin on 2018/3/7.
 * 描述：日期处理常用类
 *
 */
@SuppressLint("SimpleDateFormat")
object DateUtils {
    var dateFormat = SimpleDateFormat("yyyy-MM-dd")
    var dateFormat2 = SimpleDateFormat("yyyy-MM")
    var dateFormat3 = SimpleDateFormat("yyyy-MM-dd HH:mm")
    var dateFormat4 = SimpleDateFormat("yyyy/MM/dd HH:mm")
    var dateFormat5 = SimpleDateFormat("MM/dd HH:mm")
    var dateFormatDB = SimpleDateFormat("yyyyMMdd")// 数据库使用的日期格式
    var dataTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val Y_M_D = "yyyy-MM-dd"
    val Y_M_D_HM = "yyyy-MM-dd HH:mm"
    val Y_M_D_HMS = "yyyy-MM-dd HH:mm:ss"
    val YMD = "yyyyMMdd"
    val YMDHM = "yyyyMMddHHmm"
    val YMDHMS = "yyyyMMddHHmmss"
    val ymd = "yyyy/MM/dd"
    val ymd_HM = "yyyy/MM/dd HH:mm"
    val md_HM = "MM/dd HH:mm"
    val y_md_HM = "yyyy-MM-dd HH:mm"
    val ymd_HMS = "yyyy/MM/dd HH:mm:ss"
    /**
     *
     * @ dateTime yyyy-MM-dd HH:mm:ss
     * @return unix 时间
     */
    val currentUnixTimeStamp: String
        get() {

            val c = Calendar.getInstance()
            c.time = Date()
            return (c.timeInMillis / 1000).toString() + ""
        }

    /**
     * 获得"yyyy-MM-dd"格式的当前日期
     * @return 返回"yyyy-MM-dd"格式的当前日期
     */
    val longYMD: String
        get() = newLongYMDFormat().format(Date())
    /**
     * 2015年12月21日
     * @return
     */
    val longYMDChina: String
        get() {
            val str = newLongYMDFormat().format(Date())
            return str.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] + "年" + str.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1] + "月" + str.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[2] + "日"
        }

    /**
     * 获得"yyyyMMddHHmmss"格式的当前日期
     * @return 返回"yyyyMMddHHmmss"格式的当前时间
     */
    val shortYMDHMS: String
        get() = newShortYMDHMSFormat().format(Date())

    /**
     *
     * 功能：生成日期yyyyMMdd
     *
     * @return
     */
    val dbDate: String
        get() = dateFormatDB.format(Date())

    /**
     * 功能：获取当前月的第一天日期
     *
     * @return
     */
    val monFirstDay: Date
        get() {
            val date = Date()
            val c = Calendar.getInstance()
            c.set(getYear(date), getMonth(date) - 1, 1)
            return c.time
        }

    /**
     * 功能：获取当前月的最后一天日期
     *
     * @return
     */
    val monLastDay: Date
        get() {
            val date = Date()
            val c = Calendar.getInstance()
            c.set(getYear(date), getMonth(date), 1)
            c.timeInMillis = c.timeInMillis - 24 * 3600 * 1000
            return c.time
        }
    /**
     * 功能：获取上个月的最后一天日期
     *
     * @return
     */
    val monUpDay: Date
        get() {
            val date = Date()
            val c = Calendar.getInstance()
            c.set(getYear(date), getMonth(date) - 1, 1)
            c.timeInMillis = c.timeInMillis - 24 * 3600 * 1000
            return c.time
        }

    /** 获得本月的第一天的日期  */
    val currMonthFirstDay: String
        get() {
            val cal = Calendar.getInstance()
            return getYear(cal).toString() + "-" + getMonth(cal) + "-01"
        }
    /** 获得当前月份2015-11  */
    val currMonth: String?
        get() {
            val cal = Calendar.getInstance()
            val s = getYear(cal).toString() + "-" + getMonth(cal)
            return getDateStrFormat2(s)
        }

    /** 获得本月的最后一天的日期  */
    val currMonthLastDay: String
        get() {
            val cal = Calendar.getInstance()
            return getYear(cal).toString() + "-" + getMonth(cal) + "-" + getDays(cal)
        }

    /**
     * 功能：获取当前日期 格式:2008-02-02 23:11:10
     *
     * @return
     */
    val currentDateTime: String
        get() {
            val date = Date()
            return dataTimeFormat.format(date)
        }

    /**
     * 功能：获取当前日期 格式:20101010
     *
     * @return
     */
    val currentDate: String
        get() {
            val date = Date()
            return dateFormat.format(date)
        }

    val currentShortDate: String
        get() {
            val date = Date()
            return dateFormat4.format(date)
        }

    /**
     * 获得"yyyyMMdd"格式的当前日期
     * @return 返回"yyyyMMdd"格式的当前日期
     */
    val shortYMD: String
        get() = newShortYMDFormat().format(Date())

    /**
     * 获取8位随机数
     * @return
     */
    val randomNum: String
        get() {
            val r = Random()
            val a = r.nextInt(99999999)
            var cardNum = "" + a
            val length = cardNum.length
            if (length < 8) {
                for (j in 0 until 8 - length) {
                    cardNum = "0" + cardNum
                }
            }
            return cardNum
        }
    val ssTimeStamp: String
        get() {
            val d = Date()
            val sdf = SimpleDateFormat("HH:mm:ss:SS")
            return sdf.format(d)
        }

    /**
     * 获取当前日期是星期几<br></br>
     *
     * @return 当前日期是星期几
     */
    //        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
    //        cal.setTime(dt);
    val weekOfDate: String
        get() {
            val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
            val cal = Calendar.getInstance()
            var w = cal.get(Calendar.DAY_OF_WEEK) - 1
            if (w < 0)
                w = 0
            return weekDays[w]
        }
    //			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    val lastMonthDay: Date
        get() {
            val calendar = Calendar.getInstance()
            val month = calendar.get(Calendar.MONTH)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            return calendar.time
        }

    //String 类型获取时间戳
    fun stringTimeChuo(date: String): Long? {
        var chuo: Long? = null
        try {
            chuo = dateFormat3.parse(date).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return chuo
    }

    //String 类型获取时间戳
    fun stringDayTimeChuo(date: String): Long? {
        var chuo: Long? = null
        try {
            chuo = dateFormat.parse(date).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return chuo
    }

    //String 类型获取时间戳
    fun stringtoTime(date: String): String {
        try {
            return dateFormat.format(dateFormat.parse(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 两时间相减 返回
     * @param startTime
     * @param endTime
     * @return
     */
    fun getSubTwoTime(endTime: String, startTime: String): String {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        try {

            val d1 = df.parse(startTime)
            val d2 = df.parse(endTime)
            val diff = d1.time - d2.time//这样得到的差值是微秒级别
            val days = diff / (1000 * 60 * 60 * 24)

            var hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
            var minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
            //	  System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
            if (hours < 0) {
                hours = BigDecimal(hours).abs().toInt().toLong()
            }
            if (minutes < 0) {
                minutes = BigDecimal(minutes).abs().toInt().toLong()
            }
            return "$days-$hours-$minutes"
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 两时间相减 返回
     * @param startTime
     * @param endTime
     * @return
     */
    fun getSubTwoTimeYY(endTime: String, startTime: String): String {
        val df = SimpleDateFormat("yyyy-MM-dd")

        try {
            val d1 = df.parse(startTime)
            val d2 = df.parse(endTime)
            val diff = d1.time - d2.time//这样得到的差值是微秒级别
            val days = diff / (1000 * 60 * 60 * 24)
            return "" + days
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     *
     * @param dateTime yyyy-MM-dd HH:mm:ss
     * @return unix 时间
     */
    fun getUnixTimeStamp(dateTime: String): String {

        val c = Calendar.getInstance()
        try {
            c.time = dataTimeFormat.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return (c.timeInMillis / 1000).toString() + ""
    }

    /**
     * unix 时间 转换
     * @param timestampString  1252639886
     * @param formats
     * @return
     */
    fun gerUnixTime2String(timestampString: String, formats: String): String {

        if (StringUtils.isBlank(timestampString) || "null" == timestampString) {
            return ""
        }
        val timestamp = java.lang.Long.parseLong(timestampString) * 1000
        return SimpleDateFormat(formats).format(Date(timestamp))
    }

    fun formatDateTime(date: Date): String {
        return dataTimeFormat.format(date)
    }


    /**
     * 创建一个"yyyy-MM-dd"日期的格式化对象
     * @return "yyyy-MM-dd"日期的格式化对象
     */
    fun newLongYMDFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd")
    }

    /**
     * 创建一个"yyyy-MM-dd HH:mm:ss"日期的格式化对象
     * @return "yyyy-MM-dd HH:mm:ss"日期的格式化对象
     */
    fun newLongYMDHMSFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    }

    /**
     * 创建一个"yyyy-MM-dd HH:mm"日期的格式化对象
     * @return "yyyy-MM-dd HH:mm"日期的格式化对象
     */
    fun newLongYMDHMFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd HH:mm")
    }


    /**
     * "yyyyMMddHHmmss"格式的日期转换为"yyyy-MM-dd HH:mm:ss"格式的日期
     * @param shortYMDHMS "yyyyMMddHHmmss"格式的日期
     * @return "yyyy-MM-dd HH:mm:ss"格式的日期
     * @throws ParseException
     */
    @Throws(ParseException::class)
    fun toLongYMDHMS(shortYMDHMS: String): String {
        return newLongYMDHMSFormat().format(newShortYMDHMSFormat().parse(shortYMDHMS))
    }

    /**
     * 创建一个"yyyyMMdd"日期的格式化对象
     * @return "yyyyMMdd"日期的格式化对象
     */
    private fun newShortYMDFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyyMMdd")
    }

    /**
     * 创建一个"yyyyMMddHHmmss"日期的格式化对象
     * @return "yyyyMMddHHmmss"日期的格式化对象
     */
    private fun newShortYMDHMSFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyyMMddHHmmss")
    }

    /**
     * "yyyyMMdd"格式的日期转换为"yyyy-MM-dd"格式的日期
     * @param shortYMD "yyyyMMdd"格式的日期
     * @return "yyyy-MM-dd"格式的日期
     * @throws ParseException
     */
    fun toLongYMD(shortYMD: String): String {
        try {
            return newLongYMDFormat().format(newShortYMDFormat().parse(shortYMD))
        } catch (e: ParseException) {
            // Auto-generated catch block

            e.printStackTrace()
            return ""
        }

    }

    /**
     *
     * 功能：把日期yyyy-MM-dd格式转换成yyyyMMDD日期格式
     *
     * @param dateStr
     * @return
     */
    fun convertClientDateToDbDate(dateStr: String): String? {
        var dbDateStr: String? = null
        try {
            dbDateStr = dateFormatDB.format(dateFormat.parse(dateStr))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dbDateStr
    }

    /**
     *
     * 功能：解析数据库中的日期字符串 格式:yyyy-MM-dd
     *
     * @param dateStr
     * @return
     */
    fun parseDate(dateStr: String): Date? {
        var date: Date? = null
        try {
            date = dateFormat.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    /**
     * 将UNIX时间转换成标准时间
     * @param timestampString
     * @return
     */
    fun getDate(timestampString: String): String {
        val timestamp = java.lang.Long.parseLong(timestampString) * 1000
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(timestamp))
    }

    /**
     *
     * 功能：格式化日期字符串
     * 例如：2008-8-8  转换为2008-08-08
     *
     * @param dateStr
     * @return
     */
    fun getDateStrFormat(dateStr: String): String? {
        try {
            val date = dateFormat.parse(dateStr)
            return dateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     *
     * 功能：格式化日期字符串
     * 例如：2008-8  转换为2008-08
     *
     * @param dateStr
     * @return
     */
    fun getDateStrFormat2(dateStr: String): String? {
        try {
            val date = dateFormat2.parse(dateStr)
            return dateFormat2.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    fun getDateStrFormat4(dateStr: String): String? {
        try {
            val date = dateFormat4.parse(dateStr)
            return dateFormat4.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 2008-8-8 转 20080808
     * @param dateStr
     * @return
     */
    fun getDateStrFormatyyyyMMdd(dateStr: String): String? {
        try {
            val date = dateFormat.parse(dateStr)
            return dateFormatDB.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     *
     * 功能：解析数据库中的时间字符串 格式:yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    fun parseDateTime(dateTimeStr: String): Date? {
        var date: Date? = null
        try {
            date = dataTimeFormat.parse(dateTimeStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param startDate
     * 开始时间
     * @param endDate
     * 结束时间
     * @return
     */
    fun calcDays(startDate: String, endDate: String): Int {
        val days: Int
        try {
            val start = dateFormat3.parse(startDate).time
            val end = dateFormat3.parse(endDate).time
            days = Math.ceil(((end - start).toFloat() / (24 * 60 * 60 * 1000).toFloat()).toDouble()).toInt()
        } catch (e: ParseException) {
            e.printStackTrace()
            return -1
        }

        return days
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param startDate
     * 开始时间
     * @param endDate
     * 结束时间
     * @return
     */
    fun calcDay(startDate: String, endDate: String): Int {
        val days: Int
        try {
            val start = dateFormatDB.parse(startDate).time
            val end = dateFormatDB.parse(endDate).time
            days = ((end - start) / (24 * 60 * 60 * 1000)).toInt()
        } catch (e: ParseException) {
            e.printStackTrace()
            return -1
        }

        return days
    }

    /**
     * 功能：指定日期加上指定天数
     *
     * @param date
     * 日期
     * @param day
     * 天数
     * @return 返回相加后的日期
     */
    fun addDate(date: Date, day: Int): Date {
        val c = Calendar.getInstance()
        c.timeInMillis = getMillis(date) + day.toLong() * 24 * 3600 * 1000
        return c.time
    }

    /**
     * 功能：指定日期加上指定天数
     *
     * @param date
     * 日期
     * @param minute
     * 分钟
     * @return 返回相加后的日期
     */
    fun addMinute(date: Date, minute: Int): Date {
        val c = Calendar.getInstance()
        c.timeInMillis = getMillis(date) + minute.toLong() * 60 * 1000
        return c.time
    }

    /**
     *
     * 功能：添加指定秒杀的时间
     *
     * @param date
     * @param second
     * @return
     */
    fun addSecond(date: Date, second: Int): Date {
        var s = date.time
        s = s + second * 1000
        return Date(s)
    }

    /**
     * 功能：指定日期减去指定天数
     *
     * @param date
     * @param day
     * @return
     */
    fun diffDate(date: Date, day: Int): Date {
        val c = Calendar.getInstance()
        c.timeInMillis = getMillis(date) - day.toLong() * 24 * 3600 * 1000
        return c.time
    }

    /**
     *
     * 功能：分钟相差 minute的时间值
     *
     * @param date
     * @param minute
     * @return
     */
    fun getDateByMinuteAdd(date: Date, minute: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MINUTE, minute)
        return calendar.time
    }

    /**
     * 功能:两个日期相隔天数
     *
     * @param startDate
     * 开始日期
     * @param endDate
     * 结束日期
     * @return 返回相减后的日期
     */
    fun diffDate(startDate: Date, endDate: Date): Int {
        val endMillis = endDate.time
        val startMillis = startDate.time
        val s = (endMillis - startMillis) / (24 * 3600 * 1000)
        return s.toInt()
    }

    @SuppressLint("SimpleDateFormat")
            /**
             * 功能：传入时间按所需格式返回时间字符串
             *
             * @param date
             * java.util.Date格式
             * @param format
             * yyyy-MM-dd HH:mm:ss | yyyy年MM月dd日 HH时mm分ss秒
             * @return
             */
    fun format(date: Date?, format: String): String {
        var date = date
        var format = format
        var result = ""
        try {
            if (date == null) {
                date = Date()// 如果时间为空,则默认为当前时间
            }
            if (StringUtils.isBlank(format)) {// 默认格式化形式
                format = "yyyy-MM-dd"
            }
            val df = SimpleDateFormat(format)
            result = df.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    @SuppressLint("SimpleDateFormat")
            /**
             * 功能：传入时间字符串按所需格式返回时间
             *
             * @param dateStr
             * 时间字符串
             * @param format
             * 跟传入dateStr时间的格式必须一样 yyyy-MM-dd HH:mm:ss | yyyy年MM月dd日 HH时mm分ss秒
             * @return
             */
    fun format(dateStr: String, format: String): Date? {
        var format = format
        if (StringUtils.isBlank(dateStr)) {
            return Date()
        }
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd"
        }
        var date: Date? = null
        try {
            val f = SimpleDateFormat(format)
            date = f.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date

    }

    /**
     * 功能：时间字符串格式转换
     *
     * @param dateStr
     * 时间字符串
     * @param format
     * 时间字符串的格式
     * @param toFormat
     * 格式为的格式
     * @return
     */
    fun format(dateStr: String, format: String, toFormat: String): String {
        return format(format(dateStr, format), toFormat)
    }

    /**
     * 功能：格式化rss的时间
     * 输入:
     * @param date
     * @return
     */
    fun formatRssDate(date: Date?): String {
        var date = date
        if (date == null) {
            date = Date()// 如果时间为空,则默认为当前时间
        }
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
        val zone = SimpleTimeZone(8, "GMT")
        sdf.timeZone = zone
        return sdf.format(date)
    }

    /**
     * 功能：返回年
     *
     * @param date
     * @return
     */
    fun getYear(date: Date?): Int {
        var date = date
        if (date == null) {
            date = Date()
        }
        val c = Calendar.getInstance()
        c.time = date
        return c.get(Calendar.YEAR)

    }

    /**
     * 功能：返回月
     *
     * @param date
     * @return
     */
    fun getMonth(date: Date?): Int {
        var date = date
        if (date == null) {
            date = Date()
        }
        val c = Calendar.getInstance()
        c.time = date
        return c.get(Calendar.MONTH) + 1
    }

    /**
     * 功能：返回日
     *
     * @param date
     * @return
     */
    fun getDay(date: Date?): Int {
        var date = date
        if (date == null) {
            date = Date()
        }
        val c = Calendar.getInstance()
        c.time = date
        return c.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 功能：返回小时
     *
     * @param date
     * @return
     */
    fun getHour(date: Date?): Int {
        var date = date
        if (date == null) {
            date = Date()
        }
        val c = Calendar.getInstance()
        c.time = date
        return c.get(Calendar.HOUR_OF_DAY)
    }

    /**
     * 功能：返回分
     *
     * @param date
     * @return
     */
    fun getMinute(date: Date?): Int {
        var date = date
        if (date == null) {
            date = Date()
        }
        val c = Calendar.getInstance()
        c.time = date
        return c.get(Calendar.MINUTE)
    }

    /**
     * 功能：返回星期 1：星期一，2:星期二 ... 6:星期六 7:星期日
     *
     * @param date
     * @return
     */
    fun getWeek(date: Date): Int {
        val c = Calendar.getInstance()
        c.time = date
        val week = c.get(Calendar.DAY_OF_WEEK) - 1
        return if (week == 0) {
            7
        } else {
            week
        }
    }

    fun getChinaWeek(date: Date): String {
        val c = Calendar.getInstance()
        c.time = date
        var str = ""
        val week = c.get(Calendar.DAY_OF_WEEK) - 1
        if (week == 0) {
            str = "星期天"
        } else {
            when (week) {
                1 -> str = "星期一"
                2 -> str = "星期二"
                3 -> str = "星期三"
                4 -> str = "星期四"
                5 -> str = "星期五"
                6 -> str = "星期六"
            }
        }
        return str
    }

    /**
     * 功能：返回秒
     *
     * @param date
     * @return
     */
    fun getSecond2(date: Date?): Int {
        var date = date
        if (date == null) {
            date = Date()
        }
        val c = Calendar.getInstance()
        c.time = date
        return c.get(Calendar.SECOND)
    }

    /**
     * 功能：返回毫秒
     *
     * @param date
     * @return
     */
    fun getMillis(date: Date?): Long {
        var date = date
        if (date == null) {
            date = Date()
        }
        val c = Calendar.getInstance()
        c.time = date
        return c.timeInMillis
    }

    /** 获得给定日期当月的天数  */
    fun getDays(cal: Calendar): Int {
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    /** 获得给定日历的年  */
    fun getYear(cal: Calendar): Int {
        return cal.get(Calendar.YEAR)
    }

    /** 获得给定日历的月  */
    fun getMonth(cal: Calendar): Int {
        return cal.get(Calendar.MONTH) + 1
    }

    /** 获得给定日期字符串对应的年  */
    fun getYear(date_str: String, type: String): Int {
        return convertStrToCal(date_str, type).get(Calendar.YEAR)
    }

    /** 日期转日历*  */
    fun convertDateToCal(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    /** 字符转换日历(动态格式转换)  */
    fun convertStrToCal(date_str: String, type: String): Calendar {
        val cal = Calendar.getInstance()
        cal.time = convertStrToDate(date_str, type)
        return cal
    }

    /** 字符转换日期(动态格式转换)  */
    fun convertStrToDate(date_str: String, type: String): Date? {
        val dateformat = SimpleDateFormat(type)
        try {
            return dateformat.parse(date_str)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    /**
     * 创建一个"yyyyMM"日期的格式化对象
     * @return "yyyyMM"日期的格式化对象
     */
    private fun newShortYMFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyyMM")
    }

    /**
     * 获得距离输入月份的diffMonth月的日期
     * @param month "yyyyMM"格式的日期
     * @param diffMonth 相差的月数
     * @return "yyyyMM"格式的日期
     * @throws ParseException
     */
    fun getShortYMDiffMonth(month: String, diffMonth: Int): String {
        val sdf = newShortYMFormat()
        try {
            sdf.parse(month)
        } catch (e: ParseException) {
            // Auto-generated catch block
            e.printStackTrace()
        }

        val c = sdf.calendar
        c.add(Calendar.MONTH, diffMonth)
        return sdf.format(c.time)
    }

    /**
     * 获得距离给定日期diffDay天的日期
     * @param shortYMD "yyyyMMdd"格式的日期
     * @param diffDay 相差的天数
     * @return "yyyyMMdd"格式的日期
     * @throws ParseException
     */
    fun getShortYMDDiffDay(shortYMD: String, diffDay: Int): String {
        val sdf = newShortYMDFormat()
        try {
            sdf.parse(shortYMD)
        } catch (e: ParseException) {
            // Auto-generated catch block
            e.printStackTrace()
        }

        val c = sdf.calendar
        c.add(Calendar.DATE, diffDay)
        return sdf.format(c.time)
    }

    /**
     * 当前时间加 减days
     * @param diffDay
     * @return
     */
    fun getAddDay(diffDay: Int): String {
        val sf = SimpleDateFormat("yyyy年MM月dd日 HH:mm")
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, diffDay)
        return sf.format(c.time)
    }

    /**
     * 获取某月份的最后一天
     * @param shortYM 月份
     * @return 输入月份的最后一天
     * @throws Exception
     */
    fun getEndDayOfMonth(shortYM: String): String {
        var month = ""
        try {
            month = getShortYMDiffMonth(shortYM, 1)
        } catch (e: Exception) {
            // Auto-generated catch block
            e.printStackTrace()
        }

        return getShortYMDDiffDay(month + "01", -1)
    }

    /**
     * 验证输入的日期是否合法
     * @param expDate
     * @return
     */
    fun checkExpDate(expDate: String): Boolean {
        val year = Integer.parseInt(expDate.substring(0, 4))
        val month = Integer.parseInt(expDate.substring(4, 6))
        val day = Integer.parseInt(expDate.substring(6, 8))
        if (month > 12 || month < 1) {
            return false
        }

        val monthLengths = intArrayOf(0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

        if (isLeapYear(year)) {
            monthLengths[2] = 29
        } else {
            monthLengths[2] = 28
        }

        val monthLength = monthLengths[month]
        return if (day < 1 || day > monthLength) {
            false
        } else true
    }

    /**
     * 是否是闰年
     */
    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    /**
     *
     * 方法用途: 结束时间（end）与start时间进行比较<br></br>
     * 实现步骤: 如果相等返回0，如果end大于start返回1，如果end小于start返回-1<br></br>
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun compareEndAndStart(start: String, end: String): Int {
        val s: Long
        if (8 == start.length) {
            s = dateFormatDB.parse(start).time
        } else if (10 == start.length) {
            s = dateFormat.parse(start).time
        } else {
            s = dataTimeFormat.parse(start).time
        }
        val e: Long
        if (8 == end.length) {
            e = dateFormatDB.parse(end).time
        } else if (10 == end.length) {
            e = dateFormat.parse(end).time
        } else {
            e = dataTimeFormat.parse(end).time
        }
        if (e > s) {
            return 1
        } else if (e < s) {
            return -1
        }
        return 0
    }

    /**
     * 智能转换日期
     *
     * @param text
     * @return
     */
    fun smartFormat(text: String?): Date? {
        val date: Date?
        try {
            if (text == null || text.length == 0) {
                date = null
            } else if (text.length == 10) {
                date = formatStringToDate(text, Y_M_D)
            } else if (text.length == 13) {
                date = Date(java.lang.Long.parseLong(text))
            } else if (text.length == 14) {
                date = formatStringToDate(text, YMDHMS)
            } else if (text.length == 16) {
                date = formatStringToDate(text, Y_M_D_HM)
            } else if (text.length == 19) {
                date = formatStringToDate(text, Y_M_D_HMS)
            } else {
                throw IllegalArgumentException("日期长度不符合要求!")
            }
        } catch (e: Exception) {
            throw IllegalArgumentException("日期转换失败!")
        }

        return date
    }

    /**
     * 把字符串格式化成日期
     *
     * @param argDateStr
     * @param argFormat
     * @return
     */
    @Throws(Exception::class)
    fun formatStringToDate(argDateStr: String?, argFormat: String): Date {
        if (argDateStr == null || argDateStr.trim { it <= ' ' }.length < 1) {
            throw Exception("参数[日期]不能为空!")
        }
        var strFormat = argFormat
        if (StringUtils.isBlank(strFormat)) {
            strFormat = Y_M_D
            if (argDateStr.length > 16) {
                strFormat = Y_M_D_HMS
            } else if (argDateStr.length > 10) {
                strFormat = Y_M_D_HM
            }
        }
        val sdfFormat = SimpleDateFormat(strFormat)
        // 严格模式
        sdfFormat.isLenient = false
        try {
            return sdfFormat.parse(argDateStr)
        } catch (e: ParseException) {
            throw Exception(e)
        }

    }

    /**
     * 功能：返回上旬/中旬/下旬
     * 1 ：上旬  2： 中旬  3： 下旬
     * @param date
     * @return
     */
    fun getEarlyMidLate(date: Date): Int {
        val day = getDay(date)
        var earlyMidLate = 0
        if (1 <= day && day <= 10) {
            earlyMidLate = 1
        }
        if (11 <= day && day <= 20) {
            earlyMidLate = 2
        }
        if (20 < day) {
            earlyMidLate = 3
        }
        return earlyMidLate
    }

    /**
     * 将日期转换成Julian日期，即为哪一年的第几天
     * @param date
     * @author gongz
     * @return
     */
    fun dateToJulian(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR) % 100
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        return year * 1000 + dayOfYear
    }

    /**
     * 将Julian日期转化为date，即为哪一年的第几天
     * @param date
     * @return
     */
    fun JulianToDate(date: Int): Date {
        val year = date / 1000 + 2000
        val dayOfYear = date % 1000
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear)
        return calendar.time
    }

    /**
     * 返回当前月份的第一天
     * @author gongz
     * @return
     */
    fun currentMonthFirstDay(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 1) //设置为1号，当前日期既为本月第一天
        return newLongYMDFormat().format(calendar.time)
    }

    /**
     * 返回当前月份的最后一天
     * @author gongz
     * @return
     */
    fun currentMonthLastDay(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 0)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.time
    }

    /**
     * 时间减去 几小时 返回时间
     * @return
     */
    fun getSubTwoDate(strDate: String, a: Int): String {
        try {
            val dft = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val dar = Calendar.getInstance()
            dar.time = dft.parse(strDate)
            dar.add(Calendar.HOUR_OF_DAY, -a)
            return dft.format(dar.time)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 功能：判断两个时间是否相等 精确到分
     *
     * @return
     */
    fun getCurrentDate(strDate: String, date: Date): Boolean {
        val dft = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val dar = Calendar.getInstance()
        try {
            dar.time = dft.parse(strDate)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return dft.format(dar.time) == dft.format(date)
    }

    @Throws(ParseException::class)
    fun addDate1(d: Date, day: Long): Date {
        var day = day

        var time = d.time
        day = day * 24 * 60 * 60 * 1000
        time += day
        return Date(time)

    }


    fun getTime(date: Date): String {
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm")
        return format.format(date)
    }

    fun getDayTime(date: Date?): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    fun getMonthTime(date: Date?): String {
        val format = SimpleDateFormat("yyyy-MM")
        return format.format(date)
    }

    /***
     * 获取当前日期的上一月
     */
    fun getLastMonth(): String {
        val c = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.MONTH, -1)
        return newLongYMDFormat().format(c.time)
    }
  /***
     * 获取当前日期的下一月
     */
    fun getNextMonth(): String {
        val c = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.MONTH, 1)
        return newLongYMDFormat().format(c.time)
    }

    /***
     * 获取当前日期
     */
    fun getTodayDate(): String {
        val c = Calendar.getInstance()
        return newLongYMDFormat().format(c.time)
    }

    /***
     * 获取本周周一
     */
    fun getThisWeekMonday(): String {
        val cal = Calendar.getInstance()
        cal.time = Date()
        // 获得当前日期是一个星期的第几天
        val dayWeek = cal.get(Calendar.DAY_OF_WEEK)
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1)
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.firstDayOfWeek = Calendar.MONDAY
        // 获得当前日期是一个星期的第几天
        val day = cal.get(Calendar.DAY_OF_WEEK)
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.firstDayOfWeek - day)
        return newLongYMDFormat().format(cal.time)
    }


    fun getFirstOfYear():String{
        val c = Calendar.getInstance()
        c.time = Date()
        c.set(Calendar.DAY_OF_YEAR, 1)
        return newLongYMDFormat().format(c.time)
    }
    /**
     * 创建一个"yyyy-MM-dd"日期的格式化对象
     * @return "yyyy-MM-dd"日期的格式化对象
     */
    fun newChineseFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy年MM月dd日")
    }
}