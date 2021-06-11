package club.codemata.dao;

import club.codemata.entity.PropertyRepairInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IPropertyRepairInfoDao
 * @Description 物业维系订单数据库操作接口
 * @createTime 2021/04/21 11:58:00
 */
@Repository
public interface IPropertyRepairInfoDao {
    /**
     * 新增一条物业维修订单
     * @Date 2021/4/21 12:00
     * @param propertyRepairInfo 物业维修订单相关信息
     * @return int
     **/
    public int savePropertyRepairInfo(PropertyRepairInfo propertyRepairInfo) throws Exception;

    /**
     * 修改工单状态为已完成
     * @Date 2021/4/23 11:32
     * @param repairId 工单ID
     * @param orderStatus 工单状态 一定传入已完成
     * @return int
     **/
    public int updatePropertyRepairInfoOrderStatus(@Param("repairId") String repairId, @Param("orderStatus") String orderStatus) throws Exception;

    /**
     * 删除一条物业报修记录
     * @Date 2021/4/22 12:40
     * @param id 要删除的物业报修记录的ID
     * @return int
     **/
    public int removeRepairInfo(@Param("id") String id) throws Exception;

    /**
     * 根据ID查找一条物业维修记录
     * @Date 2021/4/22 12:42
     * @param id 要查找的物业维修记录的ID
     * @return club.codemata.entity.PropertyRepairInfo
     **/
    public PropertyRepairInfo getRepairInfoById(@Param("id") String id) throws Exception;

    /**
     * 根据用户ID查找该用户下单的所有物业维修记录
     * @Date 2021/4/22 12:46
     * @param userId 用户ID
     * @return java.util.List<club.codemata.entity.PropertyRepairInfo>
     **/
    public List<PropertyRepairInfo> getRepairInfosByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 根据维修人员ID查找物业维修记录
     * @Date 2021/4/22 12:48
     * @param workerId 维修人员ID
     * @return java.util.List<club.codemata.entity.PropertyRepairInfo>
     **/
    public List<PropertyRepairInfo> getRepairInfosByWorkerId(@Param("workerId") String workerId) throws Exception;

    /**
     * 根据属性及其对应值 对物业维修记录进行计数
     * @Date 2021/4/22 13:06
     * @param property 属性
     * @param value 属性对应值
     * @return int
     **/
    public int count(@Param("property") String property, @Param("value") String value) throws Exception;

    /**
     * 获取该员工未处理的工单数量
     * @Date 2021/4/22 16:15
     * @param workerId
     * @param orderStatus
     * @return int
     **/
    public int countUntreated(@Param("workerId") String workerId, @Param("orderStatus") String orderStatus) throws Exception;
}
