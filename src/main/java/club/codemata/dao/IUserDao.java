package club.codemata.dao;

import club.codemata.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IUserDao
 * @Description 用户信息数据库操作接口
 * @createTime 2021/04/11 22:08:00
 */
@Repository
public interface IUserDao {
    /**
     * 新增一个用户
     * @Date 2021/4/11 22:10
     * @param user 新增用户的基本信息
     * @return int
     **/
    public int saveUser(User user) throws Exception;

    /**
     * 修改用户基本信息
     * @Date 2021/4/11 22:12
     * @param user
     * @return int
     **/
    public int updateUser(User user) throws Exception;

    /**
     * 通过用户ID查找用户信息
     * @Date 2021/4/11 22:23
     * @param userId
     * @return club.codemata.entity.User
     **/
    public User getUserById(@Param("userId") String userId) throws Exception;

    /**
     * 根据区域号和单元号查找用户信息
     * @Date 2021/4/11 22:24
     * @param areaId 区域号
     * @param unitId 单元号
     * @return java.util.List<club.codemata.entity.User>
     **/
    public List<User> getUsersByAreaAndUnitAndRoom(@Param("areaId") String areaId, @Param("unitId") String unitId, @Param("roomId") String roomId) throws Exception;

    /**
     * 根据属性及其对应值对用户进行计数
     * 如果property为address才需要填入value1和value2
     * 如果property为用户ID 则只需要填入value1 将value2的传null
     * @Date 2021/4/11 22:29
     * @param property 属性--用户ID,所在区域号,所在单元号
     * @param value1 属性值1
     * @param value2 属性值2
     * @return int
     **/
    public int count(@Param("property") String property, @Param("value1") String value1, @Param("value2") String value2);

    public int countByUserId(@Param("userId") String userId) throws Exception;
}
