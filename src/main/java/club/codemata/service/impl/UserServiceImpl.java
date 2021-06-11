package club.codemata.service.impl;

import club.codemata.dao.IUserDao;
import club.codemata.entity.User;
import club.codemata.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName UserServiceImpl
 * @Description 用户业务层逻辑
 * @createTime 2021/04/19 20:23:00
 */
@Service(value = "UserService")
public class UserServiceImpl implements IUserService {
    private IUserDao userDao;

    @Autowired
    @Qualifier("IUserDao")
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int saveUser(String userId, String userTel, String userName, String gender, String nationality, String province, String city, String areaId, String unitId, String roomId) throws Exception {
        User user = new User();
        user.setUserId(userId);
        user.setUserTel(userTel);
        user.setUserName(userName);
        user.setGender(gender);
        user.setNationality(nationality);
        user.setProvince(province);
        user.setCity(city);
        user.setAreaId(areaId);
        user.setUnitId(unitId);
        user.setRoomId(roomId);
        return userDao.saveUser(user);
    }

    @Override
    public int updateUser(User user) throws Exception {
        return 0;
    }

    @Override
    public User getUserInfoById(String userId) throws Exception {
        return userDao.getUserById(userId);
    }

    @Override
    public int countByUserId(String userId) throws Exception {
        return userDao.countByUserId(userId);
    }
}
