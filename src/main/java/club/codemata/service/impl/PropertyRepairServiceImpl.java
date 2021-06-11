package club.codemata.service.impl;

import club.codemata.bo.RepairInfoBO;
import club.codemata.dao.*;
import club.codemata.entity.PropertyRepairInfo;
import club.codemata.entity.User;
import club.codemata.entity.Worker;
import club.codemata.service.IPropertyRepairService;
import club.codemata.utils.SMSUtils;
import club.codemata.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PropertyRepairServiceImpl
 * @Description TODO
 * @createTime 2021/04/21 12:10:00
 */
@Service(value = "PropertyRepairService")
@Transactional(rollbackFor = Exception.class)
public class PropertyRepairServiceImpl implements IPropertyRepairService {
    private IUserDao userDao;
    private IWorkerDao workerDao;
    private IPropertyRepairInfoDao repairInfoDao;
    private IPaymentDao paymentDao;
    private IQrCodeDao qrCodeDao;

    @Autowired
    @Qualifier("IUserDao")
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    @Qualifier("IWorkerDao")
    public void setWorkerDao(IWorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    @Autowired
    @Qualifier("IPropertyRepairInfoDao")
    public void setRepairInfoDao(IPropertyRepairInfoDao repairInfoDao) {
        this.repairInfoDao = repairInfoDao;
    }

    @Autowired
    @Qualifier("IPaymentDao")
    public void setPaymentDao(IPaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Autowired
    @Qualifier("IQrCodeDao")
    public void setQrCodeDao(IQrCodeDao qrCodeDao) {
        this.qrCodeDao = qrCodeDao;
    }

    /**
     * 新增一条物业维修订单
     * @Date 2021/4/21 12:13
     * @param userId 下单用户的ID
     * @param workerId 被下单的维修人员的ID
     * @param repairDescription 物业维修的要求或者描述
     * @return int
     **/
    @Override
    public synchronized PropertyRepairInfo addOrder(String userId, String workerId, String repairDescription) throws Exception {
        Worker worker = workerDao.getWorkerByWorkerId(workerId);
        PropertyRepairInfo propertyRepairInfo = new PropertyRepairInfo();
        propertyRepairInfo.setId(UUIDUtils.getUUID());
        propertyRepairInfo.setUserId(userId);
        propertyRepairInfo.setWorkerId(workerId);
        propertyRepairInfo.setCost(worker.getCost());
        propertyRepairInfo.setRepairDescription(repairDescription);
        int res = repairInfoDao.savePropertyRepairInfo(propertyRepairInfo);
        // 获取用户姓名 用于发短信
        String userLastName = userDao.getUserById(userId).getUserName().substring(0, 1);
        // 获取该条工单ID
        String[] repairId = propertyRepairInfo.getId().split("-");
        // 由于API限制每个参数不允许超过12位 因此将ID分为五段
        String repairIdSub1 = repairId[0];
        String repairIdSub2 = repairId[1];
        String repairIdSub3 = repairId[2];
        String repairIdSub4 = repairId[3];
        String repairIdSub5 = repairId[4];
        // 获取维修人员电话号码
        String phoneNumber = worker.getWorkerTel();
        // 获取该维修人员未处理的工单数量
        int repairInfoTotal = worker.getWait() + 1;
        if (res > 0) {
            // 发送短信
            SMSUtils.sendToWorkerRepairInfo(phoneNumber, userLastName, repairIdSub1, repairIdSub2, repairIdSub3, repairIdSub4, repairIdSub5, repairInfoTotal);
            workerDao.updateWorkerWait(workerId, 1);
            return propertyRepairInfo;
        } else {
            return null;
        }
    }

    @Override
    public int userDelete(String id, String type) throws Exception {
        PropertyRepairInfo repairInfo = repairInfoDao.getRepairInfoById(id);
        int res = repairInfoDao.removeRepairInfo(id);
        // 获取该工单的ID 和 维修人员的电话号码
        String phoneNumber = workerDao.getWorkerByWorkerId(repairInfo.getWorkerId()).getWorkerTel();
        String[] repairId = repairInfo.getId().split("-");
        // 将ID拆解为五个子字段
        String repairIdSub1 = repairId[0];
        String repairIdSub2 = repairId[1];
        String repairIdSub3 = repairId[2];
        String repairIdSub4 = repairId[3];
        String repairIdSub5 = repairId[4];
        // 删除成功 并且是执行取消操作 那么给维修人员发送消息 通知该订单已经取消
        if (res > 0 && "取消".equals(type)) {
            SMSUtils.sendToWorkerCancelRepair(repairIdSub1, repairIdSub2, repairIdSub3, repairIdSub4, repairIdSub5, phoneNumber);
            workerDao.updateWorkerWait(workerDao.getWorkerByWorkerId(repairInfo.getWorkerId()).getWorkerId(), -1);
        } else if (res > 0) {
            // 删除对应的账单信息和支付码信息
            // 删除账单信息
            paymentDao.removePayment(id);
            // 删除支付码信息
            qrCodeDao.removeQrCode(id);
        }
        return res;
    }

    /**
     * 删除一条物业维修记录
     * @Date 2021/4/22 13:14
     * @param id 要删除的物业维修记录的ID
     * @return it
     **/
    @Override
    public int removeRepairInfo(String id) throws Exception {
        return 0;
    }

    /**
     * 根据属性和值查找物业维修记录
     * @Date 2021/4/22 13:15
     * @param property 属性
     * @param value 值
     * @return java.util.List<club.codemata.bo.RepairInfoBO>
     **/
    @Override
    public List<RepairInfoBO> getRepairInfos(String property, String value) throws Exception {
        List<RepairInfoBO> repairInfoBOS = new ArrayList<>();
        if ("用户ID".equals(property)) {
            List<PropertyRepairInfo> repairInfos = repairInfoDao.getRepairInfosByUserId(value);
            if (repairInfos.size() > 0) {
                for (PropertyRepairInfo repairInfo : repairInfos) {
                    repairInfoBOS.add(repairInfo2RepairInfoBO(repairInfo));
                }
            }
        }
        return repairInfoBOS;
    }

    @Override
    public int count(String property, String value) throws Exception {
        return repairInfoDao.count(property, value);
    }

    /**
     * 将PropertyRepairInfo的对象封装为RepairIndoBO的对象
     * @Date 2021/4/22 13:29
     * @param repairInfo 要封装的PropertyRepairInfo对象
     * @return club.codemata.bo.RepairInfoBO
     **/
    @Override
    public RepairInfoBO repairInfo2RepairInfoBO(PropertyRepairInfo repairInfo) throws Exception {
        // 查找对应用户信息
        User user = userDao.getUserById(repairInfo.getUserId());
        // 查找维修人员信息
        Worker worker = workerDao.getWorkerByWorkerId(repairInfo.getWorkerId());
        // 查找支付记录
        int payStatus = paymentDao.countPaymentByBillId(repairInfo.getId());
        RepairInfoBO repairInfoBO = new RepairInfoBO();
        repairInfoBO.setRepairId(repairInfo.getId());
        repairInfoBO.setRepairDescription(repairInfo.getRepairDescription());
        repairInfoBO.setCost(repairInfo.getCost());
        repairInfoBO.setUserId(repairInfo.getUserId());
        repairInfoBO.setUserName(user.getUserName());
        repairInfoBO.setUserTel(user.getUserTel());
        repairInfoBO.setWorkerId(repairInfo.getWorkerId());
        repairInfoBO.setWorkerName(worker.getWorkerName());
        repairInfoBO.setWorkerTel(worker.getWorkerTel());
        repairInfoBO.setOrderDate(repairInfo.getOrderDate());
        repairInfoBO.setOrderStatus(repairInfo.getOrderStatus());
        repairInfoBO.setPayStatus(payStatus >= 1 ? "已支付" : "未支付");
        return repairInfoBO;
    }
}
