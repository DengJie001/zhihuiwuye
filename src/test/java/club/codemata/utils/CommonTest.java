package club.codemata.utils;

import club.codemata.dao.IEquipmentDao;
import club.codemata.entity.Equipment;
import club.codemata.entity.Payment;
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
        System.out.println(str.substring(0, 1));
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

    @Test
    public void testCharInString() {
        String str = "123456789/asdasda";
        str = str.substring(0, str.indexOf("/"));
        System.out.println(str);
    }

    @Test
    public void testFloat() {
        Payment payment = new Payment();
        payment.setFigure(new Integer("12"));
        System.out.println(payment);
    }

    @Test
    public void test() {
        String str = "2021581620474086433/1620475179214";
        if (str.indexOf("/") != -1) {
            System.out.println(str.substring(0, str.indexOf("/")));
        } else {
            System.out.println(str);
        }
    }

    @Test
    public void test01() {
        int res = 0;
        List<int[]> list = new ArrayList<>();
        Vector<int[]> v = new Vector<>();
        HashMap<String, Integer> hash = new HashMap<>();
        for (int i = 0; i <= 8; ++i) {
            for (int j = 0; j <= 8; ++j) {
                for (int k = 0; k <= 8; ++k) {
                    if (i + j + k == 12) {
                        int[] arr = {i, j , k};
                        res += 1;
                        Arrays.sort(arr);
                        String str = String.valueOf(arr[0]) + String.valueOf(arr[1]) + String.valueOf(arr[2]);
                        if (hash.get(str) == null) {
                            hash.put(str, 1);
                        } else {
                            Integer total = hash.get(str);
                            hash.remove(str);
                            hash.put(str, total + 1);
                        }
                    }
                }
            }
        }
        for (Map.Entry<String, Integer> entry : hash.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue() + ":" + (float)entry.getValue() / res);
        }
        System.out.println(res);
    }
}
