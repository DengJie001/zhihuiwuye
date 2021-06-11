package club.codemata.dao;

import club.codemata.entity.Manager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IManagerDao
 * @Description 管理员信息数据库操作接口
 * @createTime 2021/03/03 20:44:00
 */
@Repository
public interface IManagerDao {
    /**
     * @author DengJie
     * @description 新增一个管理员基本信息
     * @Date 2021/3/3 20:44
     * @Param [club.codemata.entity.Manager]
     * @return int
     */
    public int saveManagerInfo(Manager manager);

    /**
     * @author DengJie
     * @description 根据管理员id删除一条管理员基本信息
     * @Date 2021/3/3 20:45
     * @Param [java.lang.String]
     * @return int
     */
    public int removeManagerInfo(@Param("managerId") String managerId);

    /**
     * @author DengJie
     * @description 修改除管理员id以外的任意基本信息
     * @Date 2021/3/3 20:46
     * @Param [club.codemata.entity.Manager]
     * @return int
     */
    public int updateManagerInfo(Manager manager);

    /**
     * 修改管理员登录密码
     * @Date 2021/5/8 0:51
     * @param managerId 管理员账号
     * @param password 新密码
     * @return int
     **/
    public int updateManagerPassword(@Param("managerId") String managerId, @Param("password") String password) throws Exception;

    /**
     * @author DengJie
     * @description 根据管理员id查询一条管理员身份信息
     * @Date 2021/3/3 20:46
     * @Param [java.lang.String]
     * @return club.codemata.entity.Manager
     */
    public Manager getManagerById(@Param("managerId") String managerId);

    /**
     * @author DengJie
     * @description 根据管理员电话号码查询一条管理员身份信息
     * @Date 2021/3/3 20:47
     * @Param [java.lang.String]
     * @return club.codemata.entity.Manager
     */
    public Manager getManagerByTel(@Param("managerTel") String managerTel);

    /**
     * @author DengJie
     * @description 根据管理员姓名查询管理员身份信息
     * @Date 2021/3/8 18:18
     * @Param [java.lang.String]
     * @return java.util.List<club.codemata.entity.Manager>
     */
    public List<Manager> getManagersByName(@Param("managerName") String managerName);

    /**
     * @author DengJie
     * @description 查询所有的管理员身份信息
     * @Date 2021/3/3 20:48
     * @Param []
     * @return java.util.List<club.codemata.entity.Manager>
     */
    public List<Manager> getAllManagers();

    /**
     * @author DengJie
     * @description 核验管理员id对应的密码
     * @Date 2021/3/3 20:51
     * @Param [java.lang.String]
     * @return int
     */
    public int verifyPassword(@Param("managerId") String managerId, @Param("password") String password);

    /**
     * @author DengJie
     * @description 统计一共有多少条记录
     * @Date 2021/3/6 16:43
     * @Param []
     * @return int
     */
    public int count(@Param("property") String property, @Param("keyWords") String keyWords);
}
