package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duanshupeng on 2017/3/22.
 * 查询当前日期的前X天
 */
public class DateUtils {

    /**
     * Created by duanshupeng on 2017/4/7.
     * 格式化时间字符串
     */
    public static String dataFormat(Date str){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(str);
    }

    /**
     *
     * 查询当前日期前(后)x天的日期
     *
     * @param date 当前日期
     * @param day 天数（如果day数为负数,说明是此日期前的天数）
     * @return yyyy-MM-dd
     */
    public static String beforNumberDay(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, day);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    public static String beforNumberDay(String date, int day) {
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
        Date d =new Date();
        try {
            d = s.parse(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_YEAR, day);
        return s.format(c.getTime());
    }

    /**
     * 当前时间转为quartz表达式
     * @param date
     * @return
     */
    public static String getCron(final Date  date){
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }


    /**
     * quartz表达式转化为当前时间
     * @param cron
     * @return
     */
    public static Date getDate(final String cron) {
        if(cron == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            return null;// 此处缺少异常处理,自己根据需要添加
        }
        return date;
    }

    /**
     * 计算date1和date2的时间差，单位是秒
     * @param date1
     * @param date2
     * @return
     */
    public static Long getDateDiff(Date date1,Date date2){
        if(date1 == null || date2 == null){
            return 0L;
        }
        Long diff = date1.getTime() - date2.getTime();
        if(diff == 0){
            return 0L;
        }
        double result = Math.ceil((double)diff / 1000);
        return new Double(result).longValue();
    }

    /**
     *
     * 这个正则匹配的是日期格式为:yyyy/MM/dd或者yyyy-MM-dd
     * @param dateStr
     * @return
     */
    public static boolean isDate(String dateStr){
        String rexp = "^(((\\d{2}(([02468][048])|([13579][26]))[\\-]" +
                "((((0?[13578])|(1[02]))[\\-]" +
                "((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-](" +
                "(0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]" +
                "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]" +
                "((((0?[13578])|(1[02]))[\\-]" +
                "((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]" +
                "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))|"
                + "((\\d{2}(([02468][048])|([13579][26]))[\\/]" +
                "((((0?[13578])|(1[02]))[\\/]" +
                "((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/]" +
                "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/]" +
                "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\/]" +
                "((((0?[13578])|(1[02]))[\\/]" +
                "((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\/]" +
                "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\/]((0?[1-9])|(1[0-9])|(2[0-8])))))))";
        Pattern p = Pattern.compile(rexp);
        Matcher matcher = p.matcher(dateStr);
        if(matcher.matches()){
            return true;
        }
        return false;
    }


    /**
     * 普通格式化：yyyy-MM-dd
     * @return
     */
    public static String format(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    /**
     * 普通格式化：yyyy-MM-dd
     * @return
     */
    public static String format(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(time);
    }

    /**
     * 普通格式化：yyyy-MM-dd
     * @return
     */
    public static String format(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 带时分秒：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatAll(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    /**
     * 带时分秒：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatAll(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(time);
    }

    /**
     * 带时分秒：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatAll(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 中文：yyyy年MM月dd日
     * @return
     */
    public static String formatCN(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    /**
     * 中文：yyyy年MM月dd日
     * @return
     */
    public static String formatCN(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(time);
    }

    /**
     * 中文：yyyy年MM月dd日
     * @return
     */
    public static String formatCN(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(date);
    }

    /**
     * 中文带时分秒：yyyy年MM月dd日 HH时mm分ss秒
     * @return
     */
    public static String formatCNAll(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    /**
     * 中文带时分秒：yyyy年MM月dd日 HH时mm分ss秒
     * @return
     */
    public static String formatCNAll(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return simpleDateFormat.format(time);
    }

    /**
     * 中文带时分秒：yyyy年MM月dd日 HH时mm分ss秒
     * @return
     */
    public static String formatCNAll(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return simpleDateFormat.format(date);
    }

    /**
     * 自定义
     * @param pra
     * @return
     */
    public static String formatPramm(String pra){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pra);
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    /**
     * 自定义
     * @param pra
     * @return
     */
    public static String formatPramm(long time, String pra){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pra);
        return simpleDateFormat.format(time);
    }

    /**
     * 自定义
     * @param pra
     * @return
     */
    public static String formatPramm(Date time, String pra){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pra);
        return simpleDateFormat.format(time);
    }
}
