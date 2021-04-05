package club.codemata.utils;

import java.util.Random;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName CodeUtils
 * @Description 生成验证码的工具类
 * @createTime 2021/03/18 18:19:00
 */
public class CodeUtils {
    /**
     * @author DengJie
     * @description 生成四位验证码
     * @Date 2021/3/18 18:22
     * @Param []
     * @return java.lang.String
     */
    public static String get4Code() {
        String str = "0123456789";
        StringBuilder stringBuilder = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            stringBuilder.append(ch);
        }
        String code = stringBuilder.toString();
        return code;
    }
}
