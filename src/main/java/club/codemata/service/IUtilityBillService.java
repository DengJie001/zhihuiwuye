package club.codemata.service;

import club.codemata.bo.UtilityBillBO;
import club.codemata.entity.UtilityBill;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IUtilityBillService
 * @Description 物业账单业务层接口
 * @createTime 2021/04/26 03:02:00
 */
public interface IUtilityBillService {
    public int addUtilityBill(float billFigure, String areaId, String unitId, String roomId, String managerId, String billCategory) throws Exception;

    public int modifyUtilityBill(float billFigure, String billCategory, String billId) throws Exception;

    public List<UtilityBillBO> getUtilityBills(String property, String value, String userId) throws Exception;

    public List<UtilityBillBO> getUtilityBillsByUserId(String userId) throws Exception;

    public List<UtilityBillBO> getUtilityBillByAddress(String areaId, String unitId, String roomId, String billStatus) throws Exception;

    public int count(String property, String value, String userId) throws Exception;

    public int countByUserId(String userId) throws Exception;

    public int countByAddress(String areaId, String unitId, String roomId, String billStatus) throws Exception;

    public int urgePayBill(String areaId, String unitId, String roomId, String date, String billCategory, String managerId) throws Exception;

    public UtilityBillBO UtilityBill2UtilityBillBO(UtilityBill bill)throws Exception;
}
