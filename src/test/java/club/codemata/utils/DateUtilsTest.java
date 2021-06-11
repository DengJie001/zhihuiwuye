package club.codemata.utils;

import club.codemata.entity.UtilityBill;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName DateUtilsTest
 * @Description 日期工具类测试
 * @createTime 2021/04/12 01:48:00
 */
public class DateUtilsTest {
    @Test
    public void testCalculateDays() throws Exception {
        int res = DateUtils.calculateDays("2021-04-01", "2021-05-01");
        System.out.println(res);
    }

    @Test
    public void testCompareDate() throws Exception {
        boolean res = DateUtils.compareDate("2021-04-01", "2021-04-01");
        System.out.println(res);
    }

    @Test
    public void testGetNowDate() {
        System.out.println(DateUtils.getNowDate());
    }

    @Test
    public void testGetNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String now = sdf.format(calendar.getTime());
        System.out.println(now);
    }

    @Test
    public void testCalendar() {
        for (int i = 0; i < 1000; ++i) {
            System.out.println(new Random().nextFloat());
        }
    }
}
