package club.codemata.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
