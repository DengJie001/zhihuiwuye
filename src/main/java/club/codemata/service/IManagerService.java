package club.codemata.service;

import club.codemata.dao.IManagerDao;
import club.codemata.entity.Manager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IManagerService
 * @Description 管理员信息业务层接口
 * @createTime 2021/03/03 20:43:00
 */
public interface IManagerService {
    public int saveManagerInfo(Manager manager);

    public int removeManagerInfo(String managerId);

    public int updateManagerInfo(Manager manager);

    public int updateManagerPassword(String managerId, String password) throws Exception;

    public Manager getManagerById(String managerId);

    public Manager getManagerByTel(String managerTel);

    public List<Manager> getManagerByName(String managerName);

    public List<Manager> getAllManagers();

    public int countTotal(String property, String keyWords);

    public List<Manager> search(String property, String keyWords);

    public int managerLogin(String managerId, String password) throws Exception;
}
