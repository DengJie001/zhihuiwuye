package club.codemata.utils;

import java.util.UUID;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName UUIDUtils
 * @Description TODO
 * @createTime 2021/02/22 11:01:00
 */
public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
