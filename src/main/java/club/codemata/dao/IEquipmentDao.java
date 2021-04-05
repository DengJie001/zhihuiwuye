package club.codemata.dao;

import club.codemata.entity.Equipment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IEquipmentDao
 * @Description 设备信息数据库操作接口
 * @createTime 2021/03/09 18:58:00
 */
@Repository
public interface IEquipmentDao {
    /**
     * @author DengJie
     * @description 新增一条设备信息
     * @Date 2021/3/9 18:59
     * @Param [club.codemata.entity.Equipment]
     * @return int
     */
    public int saveEquipment(Equipment equipment);

    /**
     * @author DengJie
     * @description 根据设备id删除一条设备信息
     * @Date 2021/3/9 18:59
     * @Param [java.lang.String]
     * @return int
     */
    public int removeEquipmentById(@Param("equipmentId") String equipmentId);

    /**
     * @author DengJie
     * @description 更新设备信息
     * @Date 2021/3/9 20:14
     * @Param [club.codemata.entity.Equipment]
     * @return int
     */
    public int updateEquipment(Equipment equipment);

    /**
     * @author DengJie
     * @description 根据id查询一条记录
     * @Date 2021/3/9 20:15
     * @Param [java.lang.String]
     * @return club.codemata.entity.Equipment
     */
    public Equipment getEquipmentById(@Param("equipmentId") String equipmentId);

    /**
     * @author DengJie
     * @description 根据设备名称模糊查询设备信息
     * @Date 2021/3/9 20:19
     * @Param [java.lang.String]
     * @return java.util.List<club.codemata.entity.Equipment>
     */
    public List<Equipment> getEquipmentsByName(@Param("equipmentName") String equipmentName);

    /**
     * @author DengJie
     * @description 根据设备品牌查询设备信息
     * @Date 2021/3/9 20:23
     * @Param [java.lang.String]
     * @return java.util.List<club.codemata.entity.Equipment>
     */
    public List<Equipment> getEquipmentsByBrand(@Param("equipmentBrand") String equipmentBrand);

    /**
     * @author DengJie
     * @description 根据管理员id查询该管理员负责的所有设备
     * @Date 2021/3/9 20:24
     * @Param [java.lang.String]
     * @return java.util.List<club.codemata.entity.Equipment>
     */
    public List<Equipment> getEquipmentsByManager(@Param("managerId") String managerId);

    /**
     * @author DengJie
     * @description 查询所有的设备信息
     * @Date 2021/3/10 8:30
     * @Param []
     * @return java.util.List<club.codemata.entity.Equipment>
     */
    public List<Equipment> getAllEquipments();


    /**
     * @author DengJie
     * @description 根据属性关键字对设备信息进行计数
     * @Date 2021/3/17 23:39
     * @Param [java.lang.String, java.lang.String]
     * @return int
     */
    public int count(@Param("property") String property, @Param("keyWords") String keyWords);
}
