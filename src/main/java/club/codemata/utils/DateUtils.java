package club.codemata.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName DateUtils
 * @Description TODO
 * @createTime 2021/02/22 14:12:00
 */
public class DateUtils {
    public static Date getFormatTime(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    /**
     * 计算两个日期相差的天数
     * @Date 2021/4/12 1:47
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return int
     **/
    public static int calculateDays(String startDate, String endDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
        long start = calendar.getTimeInMillis();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        long end = calendar.getTimeInMillis();
        return (int) ((end - start) / (1000 * 60 * 60 * 24));
    }

    /**
     * 比较两个日期的大小
     * 如果date1大于date2 返回true 否则返回false
     * @Date 2021/4/12 2:02
     * @param date1 第一个时间字符串
     * @param date2 第二个时间字符串
     * @return boolean
     **/
    public static boolean compareDate(String date1, String date2) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date1));
        long millis1 = calendar.getTimeInMillis();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date2));
        long millis2 = calendar.getTimeInMillis();
        if (millis1 - millis2 > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回当前年月日,以XXXX-XX-XX的格式
     * @Date 2021/4/19 16:31
     * @return java.lang.String
     **/
    public static String getNowDate() {
        Calendar calendar = Calendar.getInstance();
        String nowDate = null;
        String strYear = "";
        String strMonth = "";
        String strDate = "";
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        strDate = String.valueOf(date);
        if (month + 1 <= 9) {
            strMonth = "0" + String.valueOf(month + 1);
        } else {
            strMonth = String.valueOf(month + 1);
        }
        strYear = String.valueOf(year);
        nowDate = strYear + "-" + strMonth + "-" + strDate;
        return nowDate;
    }

    /**
     * 获取当前时间yyyy-MM-dd HH-mm-ss
     * @Date 2021/4/21 14:54
     * @return java.lang.String
     **/
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }
}
