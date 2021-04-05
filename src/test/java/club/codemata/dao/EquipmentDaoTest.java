package club.codemata.dao;

import club.codemata.bo.EquipmentBO;
import club.codemata.entity.Equipment;
import club.codemata.entity.Manager;
import club.codemata.service.IEquipmentService;
import club.codemata.service.IManagerService;
import club.codemata.service.impl.EquipmentServiceImpl;
import club.codemata.service.impl.ManagerServiceImpl;
import club.codemata.utils.MybatisUtils;
import club.codemata.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Random;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName EquipmentDaoTest
 * @Description TODO
 * @createTime 2021/03/11 13:04:00
 */
public class EquipmentDaoTest {
    @Test
    public void testSaveEquipment() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        IEquipmentDao mapper = sqlSession.getMapper(IEquipmentDao.class);

        String[] managerIds = { "17746606528", "178206201", "178206203", "178206207", "178206216", "178206242", "18190129279" };
        String[] brands = { "iPhone", "HuaWei", "XiaoMi", "RNG", "EDG", "PFX", "TES", "WE" };
        String[] names = { "手机", "电脑", "电视机", "投影仪", "门禁", "啦啦啦" };
        String[] types = { "皇族", "国电", "59e", "小凤凰", "滔博" };
        String[] buyDates = { "2021-03-05", "2021-05-02", "2020-01-01", "2026-05-13", "2020-12-31", "2021-01-15" };
        for (int i = 1; i < 2000; i++) {
            Equipment equipment = new Equipment();
            equipment.setEquipmentId(UUIDUtils.getUUID());
            equipment.setEquipmentPicture("暂无图片");
            equipment.setEquipmentName(names[new Random().nextInt(10) % 6] + i);
            equipment.setEquipmentBrand(brands[new Random().nextInt(16) % 8] + i);
            equipment.setEquipmentType(types[new Random().nextInt(10) % 5] + i);
            equipment.setEquipmentQuantity(new Random().nextInt(100));
            equipment.setEquipmentPrice(new Random().nextFloat() * 100000);
            equipment.setManagerId(managerIds[new Random().nextInt(10) % 7]);
            equipment.setBuyDate(buyDates[new Random().nextInt(10) % 6]);

            mapper.saveEquipment(equipment);
        }
    }

    @Test
    public void testCount() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        IEquipmentDao mapper = sqlSession.getMapper(IEquipmentDao.class);
        int res = mapper.count("manager", "178206201");
        System.out.println(res);
    }
}
