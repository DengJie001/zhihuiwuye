package club.codemata.dao;

import club.codemata.entity.UtilityBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IUtilityBillDao
 * @Description 物业账单数据库实体类
 * @createTime 2021/04/26 01:13:00
 */
@Repository
public interface IUtilityBillDao {
    /**
     * 新增一条物业账单信息
     * @Date 2021/4/26 1:19
     * @param bill 新增的物业账单信息
     * @return int
     **/
    public int saveUtilityBill(UtilityBill bill) throws Exception;

    /**
     * 更新一条物业账单信息
     * @Date 2021/4/26 1:19
     * @param bill 更新后的物业账单信息
     * @return int
     **/
    public int updateUtilityBill(UtilityBill bill) throws Exception;

    public int updateUtilityBillStatus(@Param("billId") String billId, @Param("billStatus") String billStatus) throws Exception;

    /**
     * 根据账单ID查找一条账单信息
     * @Date 2021/4/26 1:19
     * @param billId 账单ID
     * @return club.codemata.entity.UtilityBill
     **/
    public UtilityBill getUtilityBill(@Param("billId") String billId) throws Exception;

    /**
     * 查询所有物业账单
     * @Date 2021/4/26 3:28
     * @return java.util.List<club.codemata.entity.UtilityBill>
     **/
    public List<UtilityBill> getAllUtilityBills() throws Exception;

    /**
     * 根据用户ID查找该用户的账单信息
     * @Date 2021/4/26 1:20
     * @param userId 用户ID
     * @return java.util.List<club.codemata.entity.UtilityBill>
     **/
    public List<UtilityBill> getUtilityBillsByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 根据账单所属用户的地址查询物业账单
     * @Date 2021/4/26 3:41
     * @param areaId 区号
     * @param unitId 单元号
     * @param roomId 房间号
     * @return java.util.List<club.codemata.entity.UtilityBill>
     **/
    public List<UtilityBill> getUtilityBillsByAddress(@Param("areaId") String areaId, @Param("unitId") String unitId, @Param("roomId") String roomId, @Param("billStatus") String billStatus) throws Exception;

    /**
     * 根据用户(非必须)和物业账单状态(已支付,未支付)查询物业账单<br>
     * 其中,如果传入的用户ID为空 那么则是查询所有用户的状态为status的物业账单
     * @Date 2021/4/26 1:24
     * @param billStatus 物业账单状态
     * @param userId 用户ID
     * @return java.util.List<club.codemata.entity.UtilityBill>
     **/
    public List<UtilityBill> getUtilityBillsByBillStatus(@Param("billStatus") String billStatus, @Param("userId") String userId) throws Exception;

    /**
     * 根据用户(非必须)和物业账单类型查询物业账单
     * @Date 2021/4/26 1:25
     * @param billCategory 物业账单类型(物业费,水费,电费)
     * @param userId 用户ID(非必须)
     * @return java.util.List<club.codemata.entity.UtilityBill>
     **/
    public List<UtilityBill> getUtilityBillsByBillCategory(@Param("billCategory") String billCategory, @Param("userId") String userId) throws Exception;

    /**
     * 根据属性及其对应值对物业账单进行计数
     * @Date 2021/4/26 1:26
     * @param property 属性--账单状态,账单类型
     * @param value 属性对应值
     * @return int
     **/
    public int count(@Param("property") String property, @Param("value") String value, @Param("userId") String userId) throws Exception;

    /**
     * 对账单通过地址进行计数
     * @Date 2021/4/26 20:49
     * @param areaId 区域号
     * @param unitId 单元号
     * @param roomId 房间号
     * @return int
     **/
    public int countByAddress(@Param("areaId") String areaId, @Param("unitId") String unitId, @Param("roomId") String roomId, @Param("billStatus") String billStatus) throws Exception;

    /**
     * 对该用户的账单进行计数
     * @Date 2021/4/26 1:57
     * @param userId 用户ID
     * @return int
     **/
    public int countByUserId(@Param("userId") String userId) throws Exception;
}
