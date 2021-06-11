package club.codemata.service;

import club.codemata.bo.RepairInfoBO;
import club.codemata.entity.PropertyRepairInfo;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IPropertyRepairService
 * @Description 物业维修订单业务层接口
 * @createTime 2021/04/21 12:09:00
 */
public interface IPropertyRepairService {
    public PropertyRepairInfo addOrder(String userId, String workerId, String repairDescription) throws Exception;

    public int userDelete(String id, String type) throws Exception;

    public int removeRepairInfo(String id) throws Exception;

    public List<RepairInfoBO> getRepairInfos(String property, String value) throws Exception;

    public int count(String property, String value) throws Exception;

    public RepairInfoBO repairInfo2RepairInfoBO(PropertyRepairInfo repairInfo) throws Exception;
}
