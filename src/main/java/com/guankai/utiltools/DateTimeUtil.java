package com.guankai.utiltools;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述：日期时间工具类
 *
 * @author guankai
 * @date 2023/9/4
 **/
public class DateTimeUtil {

    private static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    private static final String YEAR_FORMAT = "yyyy";
    private static final String YEAR_FORMAT_CN = "yyyy年";

    private static final String MONTH_FORMAT = "yyyy-MM";
    private static final String MONTH_FORMAT_SLASH = "yyyy/MM";
    private static final String MONTH_FORMAT_TRIM = "yyyyMM";
    private static final String MONTH_FORMAT_CN = "yyyy年MM月";

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_FORMAT_SLASH = "yyyy/MM/dd";
    private static final String DATE_FORMAT_TRIM = "yyyyMMdd";
    private static final String DATE_FORMAT_CN = "yyyy年MM月dd日";

    private static final String DAY_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String DAY_TIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm";
    private static final String DAY_TIME_FORMAT_TRIM = "yyyyMMddHHmm";
    private static final String DAY_TIME_FORMAT_CN = "yyyy年MM月dd日 HH时mm分";

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";
    private static final String TIME_FORMAT_TRIM = "yyyyMMddHHmmss";
    private static final String TIME_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";

    private static final String MONTH_TIME_FORMAT = "MM-dd HH:mm";
    private static final String MONTH_TIME_FORMAT_SLASH = "MM/dd HH:mm";
    private static final String MONTH_TIME_FORMAT_TRIM = "MMddHHmm";
    private static final String MONTH_TIME_FORMAT_CN = "MM月dd日 HH时mm分";

    private static final String HOUR_MINUTE_FORMAT = "HH:mm";
    private static final String HOUR_MINUTE_FORMAT_TRIM = "HHmm";
    private static final String HOUR_MINUTE_FORMAT_CN = "HH时mm分";

    private static final String HOUR_MINUTE_SECOND_FORMAT = "HH:mm:ss";
    private static final String HOUR_MINUTE_SECOND_FORMAT_TRIM = "HHmmss";
    private static final String HOUR_MINUTE_SECOND_FORMAT_CN = "HH时mm分ss秒";


    /**
     * 取得当前系统时间戳
     * 
     * @return Timestamp
     */
    public static Timestamp getCurrTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 取得当前系统时间
     * 
     * @return Date
     */
    public static Date getCurrTime() {
        return new Date();
    }

    /**
     * 取得当前系统微秒值
     * 
     * @return Long
     */
    public static Long geTmicTime() {
        // 微秒
        long cuTime = System.currentTimeMillis() * 1000;
        // 纳秒
        long nanoTime = System.nanoTime();
        return cuTime + (nanoTime - nanoTime / 1000000 * 1000000) / 1000;
    }

    /**
     * 得到格式化后的当前系统日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @return String
     */
    public static String getCurrDateStr() {
        return getDateFormat(getCurrTime());
    }

    /**
     * 得到格式化后的当前系统日期，格式为yyyyMMdd，如20060215
     *
     * @return String
     */
    public static String getCurrDateStr_TRIM() {
        return getDateFormat(getCurrTime(), DATE_FORMAT_TRIM);
    }

    /**
     * 得到格式化后的当前系统日期，格式为yyyy年MM月dd日，如2006年02月15日
     *
     * @return String
     */
    public static String getCurrDateStr_CN() {
        return getDateFormat(getCurrTime(), DATE_FORMAT_CN);
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @return String
     */
    public static String getCurrTimeStr() {
        return getTimeFormat(getCurrTime());
    }

    /**
     * 得到格式化后的当前系统日期，格式为yyyyMMddHHmmss，如20060215152345
     *
     * @return String
     */
    public static String getCurrTimeStr_TRIM() {
        return getTimeFormat(getCurrTime(), TIME_FORMAT_TRIM);
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @return String
     */
    public static String getCurrTimeStr_CN() {
        return getTimeFormat(getCurrTime(), TIME_FORMAT_CN);
    }

    /**
     * 将date转化为“yyyy-MM-dd”格式的字符串
     * 
     * @param date 待转化的日期
     * @return 转化后的日期（String类型）
     */
    public static String getDateFormat(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(date);
    }

    /**
     * 将date转化为指定格式的字符串
     * 
     * @param date 待转化的日期
     * @param format 转换格式
     * @return 转化后的日期（String类型）
     */
    public static String getDateFormat(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param dateStr 要格式化的日期
     * @return 格式化后的日期
     * @throws ParseException
     */
    public static Date getFormatDate(String dateStr) throws ParseException {
        return getFormatDate(dateStr, DATE_FORMAT);
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param dateStr 要格式化的日期
     * @param format 日期格式
     * @return 格式化后的日期
     * @throws ParseException
     */
    public static Date getFormatDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat dtFormatdB = null;
        dtFormatdB = new SimpleDateFormat(format);
        return dtFormatdB.parse(dateStr);
    }

    /**
     * 将date转化为“yyyy-MM-dd HH:mm:ss”格式的字符串
     * 
     * @param time 待转化的时间
     * @return 转化后的时间（String类型）
     */
    public static String getTimeFormat(Date time) {
        SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);
        return df.format(time);
    }

    /**
     * 将date转化为指定格式的字符串
     * 
     * @param time 待转化的时间
     * @param format 转换格式
     * @return 转化后的时间（String类型）
     */
    public static String getTimeFormat(Date time, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(time);
    }

    /**
     * 根据格式化后的时间得到日期类型，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 00:00:00
     *
     * @param timeStr 格式化后的时间
     * @return 日期类型
     * @throws ParseException
     */
    public static Date getFormatTime(String timeStr) throws ParseException {
        return getFormatTime(timeStr, TIME_FORMAT);
    }

    /**
     * 根据格式化后的时间得到日期类型
     *
     * @param timeStr 格式化后的时间
     * @return 日期类型
     * @return 格式化后的时间
     * @throws ParseException
     */
    public static Date getFormatTime(String timeStr, String format) throws ParseException {
        SimpleDateFormat dtFormat = null;
        dtFormat = new SimpleDateFormat(format);
        return dtFormat.parse(timeStr);
    }

    /**
     * 根据格式得到格式化后的日期的毫秒数
     * 
     *@param date 要格式化的日期
     *@param format 转换格式
     *@return 毫秒数
     */
    public static Long getFormatDateLong(Date date, String format) throws ParseException {
        SimpleDateFormat dtFormat = null;
        dtFormat = new SimpleDateFormat(format);
        String formatStr = getDateFormat(date,format);
        return dtFormat.parse(formatStr).getTime();
    }

    /**
     * 根据格式得到格式化后的时间的毫秒数
     * 
     *@param time 要格式化的时间
     *@param format 转换格式
     *@return 毫秒数
     */
    public static Long getFormatTimeLong(Date time, String format) throws ParseException {
        SimpleDateFormat dtFormat = null;
        dtFormat = new SimpleDateFormat(format);
        String formatStr = getDateFormat(time,format);
        return dtFormat.parse(formatStr).getTime();
    }

    /**
     * 得到指定日期的昨日时间 如果当日为2007-9-2,那么获得2007-9-1
     */
    public static String getDateBeforeDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return getDateFormat(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到指定日期的上月时间 如果当日为2007-9-1,那么获得2007-8-1
     */
    public static String getDateBeforeMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return getDateFormat(cal.getTime(), DATE_FORMAT);
    }

    /**
     * 得到日期的前或者后几天
     *
     * @param days 如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     */
    public static Date getDateBeforeOrAfter(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 得到指定日期所在月份的第一天,例如：2021-02-29 -> 2021-02-01
     */
    public static Date getLastMonthFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
        return cal.getTime();
    }

    /**
     * 得到指定日期所在月份的第一天,例如：2021-03-05 -> 2021-03-31
     */
    public static Date getLastMonthLastDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 得到日期的前或者后几小时
     *
     * @param hours 如果要获得前几小时日期，该参数为负数； 如果要获得后几小时日期，该参数为正数
     */
    public static Date getDateBeforeOrAfterHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * 若要处理的时间为当天，则显示HH:mm ; 为当年，则显示MM-dd HH:mm ; 其他情况，显示yyyy-MM-dd HH:mm
     *
     * @param timeStr 要处理的时间 格式要求 -> yyyy-MM-dd HH:mm:ss
     * @throws ParseException
     */
    public static String handleTimeFormat(String timeStr) throws ParseException{
        //当天
        String currentDay = getCurrDateStr();
        //当年
        String currentYear = getDateFormat(getCurrTime(),YEAR_FORMAT);
        if (currentDay.equals(DateTimeUtil.getDateFormat(DateTimeUtil.getFormatTime(timeStr), DATE_FORMAT))){
            return DateTimeUtil.getDateFormat(DateTimeUtil.getFormatTime(timeStr), HOUR_MINUTE_FORMAT);
        }
        return currentYear.equals(DateTimeUtil.getDateFormat(DateTimeUtil.getFormatTime(timeStr), YEAR_FORMAT)) ?
                DateTimeUtil.getDateFormat(DateTimeUtil.getFormatTime(timeStr), MONTH_TIME_FORMAT) :
                DateTimeUtil.getDateFormat(DateTimeUtil.getFormatTime(timeStr), DAY_TIME_FORMAT);
    }

    /**
     * 获取时间间隔内的每一段时间
     * 
     * @param startTime 开始时间
     * @param endTime 截止时间
     * @param format 入参时间格式
     * @param targetFormat 要返回的时间格式
     * @param unit 单位
     * @param interval 每个时间点的间隔
     * @throws ParseException
     */
    public static Map<String,String> getIntervalTimeMap(String startTime, String endTime, String format, String targetFormat, int unit, int interval) throws ParseException{
        LinkedHashMap<String,String> timeMap = Maps.newLinkedHashMap();
        while (startTime.compareTo(endTime)<=0){
            String formatDate = getDateFormat(DateTimeUtil.getFormatTime(startTime,format),targetFormat);
            timeMap.put(formatDate,formatDate);
            startTime = getDateTimeByInterval(startTime,interval,unit,format);
        }
        return timeMap;
    }

    /**
     * 获取每个点的时间
     * 
     * @throws ParseException
     */
    private static String getDateTimeByInterval(String time, int interval, int unit, String format) throws ParseException{
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = dateFormat.parse(time);
        calendar.setTime(date);
        calendar.add(unit, interval);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    /**
     * 获取两个日期之间的所有日期
     * 
     * @throws ParseException
     */
    public static List<String> getEveryDay(String startDate, String endTDate) throws ParseException {
        List<String> dates = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        Date dStart = sdf.parse(startDate);
        Date dEnd = sdf.parse(endTDate);
        dates.add(sdf.format(dStart));
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(dStart);
        while (dEnd.after(calStart.getTime())) {
            calStart.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(sdf.format(calStart.getTime()));
        }
        return dates;
    }

    /**
     * 获取两个月份之间的所有月份
     * 
     * @throws ParseException
     */
    public static List<String> getEveryMonth(String startMonth, String endMonth) throws ParseException{
        List<String> months = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat(MONTH_FORMAT);

        Date dStart = sdf.parse(startMonth);
        Date dEnd = sdf.parse(endMonth);
        months.add(sdf.format(dStart));
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(dStart);
        while (dEnd.after(calStart.getTime())) {
            calStart.add(Calendar.MONTH, 1);
            months.add(sdf.format(calStart.getTime()));
        }
        return months;
    }

    /**
     * 根据日期判断是星期几
     */
    public static int getWeekIndex(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(weekIndex<0){
            weekIndex = 0;
        }
        return weekIndex;
    }

    /**
     * 获取两个日期之间相差的天数
     */
    public static int differentDays(Date startDate, Date endDate) {
        long from = startDate.getTime();
        long to = endDate.getTime();
        return (int) ((to - from)/(1000 * 60 * 60 * 24));
    }




}
