package club.codemata.utils;

import club.codemata.dao.IEquipmentDao;
import club.codemata.entity.Equipment;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName CommonTest
 * @Description TODO
 * @createTime 2021/03/01 16:41:00
 */
public class CommonTest {
    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
    }

    @Test
    public void testSubString() {
        String str = "+http://localhost:8080";
        System.out.println(str.substring(1, str.length()));
        System.out.println(str);
    }

    @Test
    public void testListNull() {
        List<String> list = new ArrayList<>();
        System.out.println(list);
        if (list.size() == 0) {
            System.out.println("null");
        } else {
            System.out.println("not null");
        }
    }
}
