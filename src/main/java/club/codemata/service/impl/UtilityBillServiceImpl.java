package club.codemata.service.impl;

import club.codemata.bo.UtilityBillBO;
import club.codemata.dao.IManagerDao;
import club.codemata.dao.IPaymentDao;
import club.codemata.dao.IUserDao;
import club.codemata.dao.IUtilityBillDao;
import club.codemata.entity.Manager;
import club.codemata.entity.User;
import club.codemata.entity.UtilityBill;
import club.codemata.service.IUtilityBillService;
import club.codemata.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName UtilityBillServiceImpl
 * @Description 物业账单业务层逻辑
 * @createTime 2021/04/26 03:09:00
 */
@Service(value = "UtilityBillService")
public class UtilityBillServiceImpl implements IUtilityBillService {
    private IManagerDao managerDao;
    private IUserDao userDao;
    private IUtilityBillDao billDao;
    private IPaymentDao paymentDao;

    @Autowired
    @Qualifier("IManagerDao")
    public void setManagerDao(IManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @Autowired
    @Qualifier("IUserDao")
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    @Qualifier("IUtilityBillDao")
    public void setBillDao(IUtilityBillDao billDao) {
        this.billDao = billDao;
    }

    @Autowired
    @Qualifier("IPaymentDao")
    public void setPaymentDao(IPaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    /**
     * 新增一条物业账单
     * @Date 2021/4/26 3:17
     * @param billFigure 账单费用
     * @param areaId 账单所属用户所在区域
     * @param unitId 账单所属用户所在单元
     * @param roomId 账单所属用户所在房间
     * @param managerId 上传者
     * @param billCategory 账单分类
     * @return int
     **/
    @Override
    public int addUtilityBill(float billFigure, String areaId, String unitId, String roomId, String managerId, String billCategory) throws Exception {
        // 封装UtilityBill对象
        UtilityBill bill = new UtilityBill();
        bill.setBillId(UtilityBill.createBillId());
        bill.setBillFigure(billFigure);
        bill.setAreaId(areaId);
        bill.setUnitId(unitId);
        bill.setRoomId(roomId);
        bill.setManagerId(managerId);
        bill.setBillCategory(billCategory);
        List<User> users = userDao.getUsersByAreaAndUnitAndRoom(areaId, unitId, roomId);
        Calendar calendar = Calendar.getInstance();
        for (User user : users) {
            SMSUtils.sendUtilityBill(user.getUserTel(), String.valueOf(calendar.get(Calendar.MONTH)), billCategory);
        }
        return billDao.saveUtilityBill(bill);
    }

    /**
     * 修改一条账单信息
     * @Date 2021/4/26 3:18
     * @param billFigure 修改后的账单金额
     * @param billCategory 修改后的账单分类
     * @param billId 被修改的账单的ID
     * @return int
     **/
    @Override
    public int modifyUtilityBill(float billFigure, String billCategory, String billId) throws Exception {
        UtilityBill bill = new UtilityBill();
        bill.setBillId(billId);
        bill.setBillFigure(billFigure);
        bill.setBillCategory(billCategory);
        return billDao.updateUtilityBill(bill);
    }

    /**
     * 根据属性及其对应值查找物业账单<br>
     * 第三个参数userId可以传入空参数 如果传入空参数 那么就是查询符合前两个条件的所有用户的物业账单
     * @Date 2021/4/26 3:20
     * @param property 属性
     * @param value 值
     * @param userId 用户ID
     * @return java.util.List<club.codemata.bo.UtilityBillBO>
     **/
    @Override
    public List<UtilityBillBO> getUtilityBills(String property, String value, String userId) throws Exception {
        List<UtilityBillBO> utilityBillBOS = new ArrayList<>();
        if ("类别".equals(property)) {
            List<UtilityBill> bills = billDao.getUtilityBillsByBillCategory(value, userId);
            if (bills.size() > 0) {
                for (UtilityBill bill : bills) {
                    utilityBillBOS.add(UtilityBill2UtilityBillBO(bill));
                }
            }
        } else if ("状态".equals(property)) {
            List<UtilityBill> bills = billDao.getUtilityBillsByBillStatus(value, userId);
            if (bills.size() > 0) {
                for (UtilityBill bill : bills) {
                    utilityBillBOS.add(UtilityBill2UtilityBillBO(bill));
                }
            }
        } else {
            List<UtilityBill> bills = billDao.getAllUtilityBills();
            if (bills.size() > 0) {
                for (UtilityBill bill : bills) {
                    utilityBillBOS.add(UtilityBill2UtilityBillBO(bill));
                }
            }
        }
        return utilityBillBOS;
    }

    /**
     * 根据用户ID查询该用户所有的物业账单
     * @Date 2021/4/26 3:29
     * @param userId 用户ID
     * @return java.util.List<club.codemata.bo.UtilityBillBO>
     **/
    @Override
    public List<UtilityBillBO> getUtilityBillsByUserId(String userId) throws Exception {
        List<UtilityBillBO> billBOS = new ArrayList<>();
        List<UtilityBill> bills = billDao.getUtilityBillsByUserId(userId);
        if (bills.size() > 0) {
            for (UtilityBill bill : bills) {
                billBOS.add(UtilityBill2UtilityBillBO(bill));
            }
        }
        return billBOS;
    }

    /**
     * 根据账单所属用户的地址查物业账单
     * @Date 2021/4/26 3:45
     * @param areaId 区域号
     * @param unitId 单元号
     * @param roomId 房间号
     * @return java.util.List<club.codemata.bo.UtilityBillBO>
     **/
    @Override
    public List<UtilityBillBO> getUtilityBillByAddress(String areaId, String unitId, String roomId, String billStatus) throws Exception {
        List<UtilityBillBO> bos = new ArrayList<>();
        List<UtilityBill> bills = billDao.getUtilityBillsByAddress(areaId, unitId, roomId, billStatus);
        if (bills.size() > 0) {
            for (UtilityBill bill : bills) {
                bos.add(UtilityBill2UtilityBillBO(bill));
            }
        }
        return bos;
    }

    /**
     * 根据属性及其对应值进行计数
     * @Date 2021/4/26 3:31
     * @param property 属性
     * @param value 值
     * @param userId 用户ID
     * @return int
     **/
    @Override
    public int count(String property, String value, String userId) throws Exception {
        return billDao.count(property, value, userId);
    }

    /**
     * 根据用户ID对该用户的物业账单进行计数
     * @Date 2021/4/26 3:32
     * @param userId 用户ID
     * @return int
     **/
    @Override
    public int countByUserId(String userId) throws Exception {
        return billDao.countByUserId(userId);
    }

    @Override
    public int countByAddress(String areaId, String unitId, String roomId, String billStatus) throws Exception {
        return billDao.countByAddress(areaId, unitId, roomId, billStatus);
    }

    /**
     * 账单催缴费
     * @Date 2021/5/7 15:28
     * @param areaId 账单所属区域号
     * @param unitId 账单所属单元号
     * @param roomId 账单所属门牌号
     * @return int
     **/
    @Override
    public int urgePayBill(String areaId, String unitId, String roomId, String date, String billCategory, String managerId) throws Exception {
        List<User> users = userDao.getUsersByAreaAndUnitAndRoom(areaId, unitId, roomId);
        for (User user : users) {
            SMSUtils.sendUrgePayBill(user.getUserTel(), date.split("-")[0], date.split("-")[1], billCategory, managerDao.getManagerById(managerId).getManagerTel());
        }
        return 1;
    }

    /**
     * 将UtilityBill的对象封装为UtilityBillBO的对象
     * @Date 2021/4/26 3:32
     * @param bill 要被封装的UtilityBill对象
     * @return club.codemata.bo.UtilityBillBO
     **/
    @Override
    public UtilityBillBO UtilityBill2UtilityBillBO(UtilityBill bill) throws Exception {
        UtilityBillBO bo = new UtilityBillBO();
        Manager manager = managerDao.getManagerById(bill.getManagerId());
        // 填入用户相关信息
        if (bill.getUserId() == null) {
            bo.setUserId("");
            bo.setUserName("");
        } else {
            bo.setUserId(bill.getUserId());
            bo.setUserName(userDao.getUserById(bill.getUserId()).getUserName());
        }
        // 填入管理员相关信息
        bo.setManagerId(bill.getManagerId());
        bo.setManagerName(manager.getManagerName());
        bo.setManagerTel(manager.getManagerTel());
        // 填入账单相关信息
        bo.setBillId(bill.getBillId());
        bo.setBillFigure(bill.getBillFigure());
        bo.setAreaId(bill.getAreaId());
        bo.setUnitId(bill.getUnitId());
        bo.setRoomId(bill.getRoomId());
        bo.setBillCategory(bill.getBillCategory());
        bo.setBillStatus(bill.getBillStatus());
        bo.setBillDate(bill.getBillDate());
        return bo;
    }
}
