package club.codemata.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName MybatisUtils
 * @Description Mybatis工具类
 * @createTime 2021/02/22 09:33:00
 */
public class MybatisUtils {
    private static SqlSessionFactory factory = null;
    static {
        try {
            InputStream is = Resources.getResourceAsStream("conf/SqlMapConfig.xml");
            factory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession() {
        return factory.openSession(true);
    }
}
