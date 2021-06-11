package club.codemata.service.impl;

import club.codemata.dao.IManagerDao;
import club.codemata.entity.Manager;
import club.codemata.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ManagerServiceImpl
 * @Description 管理员基本信息业务层实现类
 * @createTime 2021/03/04 10:12:00
 */
@Service(value = "ManagerService")
public class ManagerServiceImpl implements IManagerService {
    private IManagerDao managerDao;

    @Autowired
    @Qualifier(value = "IManagerDao")
    public void setManagerDao(IManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @Override
    public int saveManagerInfo(Manager manager) {
        return managerDao.saveManagerInfo(manager);
    }

    /**
     * @author DengJie
     * @description 删除管理员身份信息
     * @Date 2021/3/8 18:39
     * @Param [java.lang.String]
     * @return int
     */
    @Override
    public int removeManagerInfo(String managerId) {
        if (managerId != null) {
            return managerDao.removeManagerInfo(managerId);
        }
        return -1;
    }

    /**
     * @author DengJie
     * @description 修改管理员身份信息
     * @Date 2021/3/8 18:31
     * @Param [club.codemata.entity.Manager]
     * @return int
     */
    @Override
    public int updateManagerInfo(Manager manager) {
        if (manager.getManagerId() != null && manager.getManagerTel() != null && manager.getManagerName() != null) {
            return managerDao.updateManagerInfo(manager);
        }
        return -1;
    }

    /**
     * 修改管理员密码
     * @Date 2021/5/8 0:55
     * @param managerId 管理员ID
     * @param password 新密码
     * @return int
     **/
    @Override
    public int updateManagerPassword(String managerId, String password) throws Exception {
        return managerDao.updateManagerPassword(managerId, password);
    }

    /**
     * @author DengJie
     * @description 根据管理员id查找一条管理员身份信息
     * @Date 2021/3/8 18:41
     * @Param [java.lang.String]
     * @return club.codemata.entity.Manager
     */
    @Override
    public Manager getManagerById(String managerId) {
        return managerDao.getManagerById(managerId);
    }

    /**
     * @author DengJie
     * @description 根据管理员电话号码查询一条管理员身份信息
     * @Date 2021/3/8 18:42
     * @Param [java.lang.String]
     * @return club.codemata.entity.Manager
     */
    @Override
    public Manager getManagerByTel(String managerTel) {
        return managerDao.getManagerByTel(managerTel);
    }

    /**
     * @author DengJie
     * @description 查找同名的管理员信息
     * @Date 2021/3/8 18:42
     * @Param [java.lang.String]
     * @return java.util.List<club.codemata.entity.Manager>
     */
    @Override
    public List<Manager> getManagerByName(String managerName) {
        return managerDao.getManagersByName("%" + managerName + "%");
    }

    /**
     * @author DengJie
     * @description 获取所有的管理员身份信息
     * @Date 2021/3/8 18:30
     * @Param []
     * @return java.util.List<club.codemata.entity.Manager>
     */
    @Override
    public List<Manager> getAllManagers() {
        return managerDao.getAllManagers();
    }

    /**
     * @author DengJie
     * @description 统计一共有多少条管理 员身份信息 用于分页总数
     * @Date 2021/3/18 3:23
     * @Param [java.lang.String, java.lang.String]
     * @return int
     */
    @Override
    public int countTotal(String property, String keyWords) {
        if (property != null && keyWords != null) {
            if (property.equals("managerName")) {
                keyWords = "%" + keyWords + "%";
            }
        }
        return managerDao.count(property, keyWords);
    }

    @Override
    public List<Manager> search(String property, String keyWords) {
        List<Manager> managers = new ArrayList<>();
        switch(property) {
            case "managerId":
                Manager managerById = managerDao.getManagerById(keyWords);
                if (managerById != null) {
                    managers.add(managerById);
                }
                break;
            case "managerName":
                List<Manager> managersByName = managerDao.getManagersByName("%" + keyWords + "%");
                if (managersByName.size() > 0) {
                    for (Manager manager : managersByName) {
                        managers.add(manager);
                    }
                }
                break;
            case "managerTel":
                Manager managerByTel = managerDao.getManagerByTel(keyWords);
                if (managerByTel != null) {
                    managers.add(managerByTel);
                }
                break;
        }
        return managers;
    }

    /**
     * 管理员登录
     * @Date 2021/4/20 14:18
     * @param managerId 账号
     * @param password 密码
     * @return int
     **/
    @Override
    public int managerLogin(String managerId, String password) throws Exception {
        return managerDao.verifyPassword(managerId, password);
    }
}
