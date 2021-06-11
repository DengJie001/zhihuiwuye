package club.codemata.service;

import club.codemata.entity.User;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IUserService
 * @Description 用户信息业务层接口
 * @createTime 2021/03/03 20:43:00
 */
public interface IUserService {
    public int saveUser(String userId, String userTel, String userName, String gender, String nationality, String province, String city,
                        String areaId, String unitId, String roomId) throws Exception;

    public int updateUser(User user) throws Exception;

    public User getUserInfoById(String userId) throws Exception;

    public int countByUserId(String userId) throws Exception;
}
