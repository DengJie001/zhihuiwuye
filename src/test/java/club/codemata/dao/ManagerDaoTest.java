package club.codemata.dao;

import club.codemata.entity.Manager;
import club.codemata.utils.MybatisUtils;
import club.codemata.utils.UUIDUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Random;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ManagerDaoTest
 * @Description TODO
 * @createTime 2021/03/03 20:57:00
 */
public class ManagerDaoTest {
    @Test
    public void testSaveManagerInfo() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        IManagerDao mapper = sqlSession.getMapper(IManagerDao.class);

        for (int i = 1; i <= 2000; ++i) {
            Manager manager = new Manager();
            manager.setManagerTel("id" + i);
            manager.setManagerName("姓名" + i);
            manager.setManagerAvatar("头像" + i);
            manager.setPassword("密码" + i);

            mapper.saveManagerInfo(manager);
        }

        sqlSession.close();
    }
}
