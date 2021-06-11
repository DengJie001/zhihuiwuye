package club.codemata.service.impl;

import club.codemata.bo.PlaceApplicationBO;
import club.codemata.dao.*;
import club.codemata.entity.*;
import club.codemata.service.IPlaceApplicationService;
import club.codemata.utils.DateUtils;
import club.codemata.utils.UUIDUtils;
import com.tencentcloudapi.cwp.v20180228.models.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceApplicationServiceImpl
 * @Description 场地申请业务逻辑
 * @createTime 2021/04/11 22:04:00
 */
@Service(value = "PlaceApplicationService")
@Transactional(rollbackFor = Exception.class)
public class PlaceApplicationServiceImpl implements IPlaceApplicationService {
    private IPlaceApplicationDao placeApplicationDao;
    private IManagerDao managerDao;
    private IPlaceApplicationResultDao placeApplicationResultDao;
    private IPlaceInfoDao placeInfoDao;
    private IUserDao userDao;
    private IPaymentDao paymentDao;

    @Autowired
    @Qualifier("IPlaceApplicationDao")
    public void setPlaceApplicationDao(IPlaceApplicationDao placeApplicationDao) {
        this.placeApplicationDao = placeApplicationDao;
    }

    @Autowired
    @Qualifier("IManagerDao")
    public void setManagerDao(IManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @Autowired
    @Qualifier("IPlaceApplicationResultDao")
    public void setPlaceApplicationResultDao(IPlaceApplicationResultDao placeApplicationResultDao) {
        this.placeApplicationResultDao = placeApplicationResultDao;
    }

    @Autowired
    @Qualifier("IPlaceInfoDao")
    public void setPlaceInfoDao(IPlaceInfoDao placeInfoDao) {
        this.placeInfoDao = placeInfoDao;
    }

    @Autowired
    @Qualifier("IUserDao")
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    @Qualifier("IPaymentDao")
    public void setPaymentDao(IPaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    /**
     * 新增一条场地申请记录
     * 在新增每一条申请时 都要立马新增与之对应的处理结果
     * @Date 2021/4/11 22:05
     * @param application 新增的场地申请信息
     * @return int
     **/
    @Override
    public PlaceApplication savePlaceApplication(PlaceApplication application) throws Exception {
        HashMap<Integer, Object> resHash = new HashMap<>();
        // 自动生成该条场地申请记录的ID
        application.setApplicationId(UUIDUtils.getUUID());
        // 计算需要的费用
        application.setCost(calculatePaidCost(application.getBeginDate(), application.getEndDate(), application));
        PlaceApplicationResult placeApplicationResult = new PlaceApplicationResult();
        // 新增的与该条场地申请对应的处理结果
        // resultId:自动生成
        // result:待审核
        // resultDescription:申请正在审核中!
        placeApplicationResult.setResultId(UUIDUtils.getUUID());
        placeApplicationResult.setResult("待审核");
        placeApplicationResult.setResultDescription("申请正在审核中!");
        // 将该条申请记录的resultId绑定为上面的结果的编号
        application.setResultId(placeApplicationResult.getResultId());
        // 如果出列结果插入成功 那么将与其对应的申请也插入数据库中 并且修改对应场地的状态为审核中
        int res = placeApplicationResultDao.savePlaceApplicationResult(placeApplicationResult);
        if (res > 0) {
            placeInfoDao.updatePlaceStatus(application.getPlaceId(), "审核中");
            int result = placeApplicationDao.savePlaceApplication(application);
            if (result > 0) {
                return application;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 删除一条场地申请记录
     * @Date 2021/4/12 1:35
     * @param applicationId 需要删除的场地申请记录的ID
     * @return int
     **/
    @Override
    public int removePlaceApplication(String applicationId) throws Exception {
        // 直接返回删除的结果
        return placeApplicationDao.removePlaceApplication(applicationId);
    }

    /**
     * 更新一条场地申请记录<br>
     * 返回值-1:修改后的结束时间不允许小于等于修改前的结束时间;<br>
     * @Date 2021/4/12 1:35
     * @param application 修改后的场地申请记录
     * @return int
     **/
    @Override
    public int updatePlaceApplication(PlaceApplication application) throws Exception {
        // 根据ID查出修改前的申请信息
        PlaceApplication oldApplication = placeApplicationDao.getPlaceApplicationById(application.getApplicationId());
        // 查出对应的场地信息
        PlaceInfo placeInfo = placeInfoDao.getPlaceInfoByPlaceId(application.getPlaceId());
        // 首先验证修改后的场地使用结束时间 是否小于等于 修改前的场地使用结束时间
        // 如果小于 则返回-1
        if (!DateUtils.compareDate(application.getEndDate(), oldApplication.getEndDate())) {
            return -1;
        }
        // 计算新的租用天数
        int days = DateUtils.calculateDays(application.getBeginDate(), application.getEndDate());
        // 设置新的租用费用
        application.setCost(days * placeInfo.getPlacePrice());
        // 插入
        int res = placeApplicationDao.savePlaceApplication(application);
        return res;
    }

    /**
     * 修改场地申请的审核结果
     * @Date 2021/4/14 23:30
     * @param applicationId 场地申请的ID
     * @param result 审核结果
     * @return int
     **/
    @Override
    public int updatePlaceApplicationResult(String applicationId, String result, String resultDescription, String managerId) throws Exception {
        // 先要根据申请ID查询对应的处理结果ID
        // 然后再修改该结果ID对应结果的审核结果
        PlaceApplication placeApplication = placeApplicationDao.getPlaceApplicationById(applicationId);
        if (placeApplication != null) {
            PlaceApplicationResult placeApplicationResult = new PlaceApplicationResult();
            placeApplicationResult.setResultId(placeApplication.getResultId());
            placeApplicationResult.setResultDescription(resultDescription);
            placeApplicationResult.setResult(result);
            placeApplicationResult.setManagerId(managerId);
            int res = placeApplicationResultDao.updatePlaceApplicationResult(placeApplicationResult);
            if (res > 0) {
                return res;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    /**
     * 根据属性及其对应值查询场地信息<br>
     * 如果property和value都为空 那么则是查询全部场地信息
     * @Date 2021/4/12 2:59
     * @param property 属性--场地编号,用户
     * @param value 属性对应的值
     * @return java.util.List<club.codemata.bo.PlaceApplicationBO>
     **/
    @Override
    public List<PlaceApplicationBO> getPlaceApplications(String property, Object value) throws Exception {
        List<PlaceApplicationBO> placeApplicationBOS = new ArrayList<>();
        if ("场地编号".equals(property)) {  // 根据场地编号查询该场地的申请记录
            List<PlaceApplication> placeApplications = placeApplicationDao.getPlaceApplicationsByPlaceId(new Integer(value.toString()));
            // 如果placeApplications的长度大于0 则查出了相关的数据
            // 则将其组装为PlaceApplicationBO的List对象 用于在Controller中组装成为PlaceApplicationVO的对象 方便前端进行展示
            if (placeApplications.size() > 0) {
                for (PlaceApplication placeApplication : placeApplications) {
                    placeApplicationBOS.add(placeApplication2PlaceApplicationBO(placeApplication));
                }
            }
        } else if ("用户".equals(property)) { // 根据用户ID查询该用户提交过的场地申请信息
            List<PlaceApplication> placeApplications = placeApplicationDao.getPlaceApplicationsByUserId(value.toString());
            // 如果placeApplications的长度大于0 则查出了相关的数据
            // 则将其组装为PlaceApplicationBO的List对象 用于在Controller中组装成为PlaceApplicationVO的对象 方便前端进行展示
            if (placeApplications.size() > 0) {
                for (PlaceApplication placeApplication : placeApplications) {
                    placeApplicationBOS.add(placeApplication2PlaceApplicationBO(placeApplication));
                }
            }
        } else if ("管理员".equals(property)) {
            // 现根据管理员id查出该管理员对应的处理结果
            List<PlaceApplicationResult> placeApplicationResults = placeApplicationResultDao.getPlaceApplicationResultsByManagerId(value.toString());
            // 如果placeApplicationResults的长度不为0 则证明该管理员处理过申请 再根据对应的resultId查出该管理员处理的申请
            if (placeApplicationResults.size() > 0) {
                for (PlaceApplicationResult placeApplicationResult : placeApplicationResults) {
                    PlaceApplication placeApplication = placeApplicationDao.getPlaceApplicationByResultId(placeApplicationResult.getResultId());
                    placeApplicationBOS.add(placeApplication2PlaceApplicationBO(placeApplication));
                }
            }
        } else if ("申请结果".equals(property)) {   // 根据申请结果(通过,拒绝,审核中)查询场地申请记录
            // 根据结果查询 由于申请结果和场地申请是不同的两张表 因此需要现根据申请结果查询出对应的申请结果ID
            // 再根据申请结果ID去场地申请表中 查询出对应的场地申请
            // 查询申请结果
            List<PlaceApplicationResult> results = placeApplicationResultDao.getPlaceApplicationResultsByResult(value.toString());
            // 如果results的长度大于0 则证明有对应数据 需要根据resultId去查询场地申请信息
            if (results.size() > 0) {
                for (PlaceApplicationResult result : results) {
                    PlaceApplication placeApplication = placeApplicationDao.getPlaceApplicationByResultId(result.getResultId());
                    placeApplicationBOS.add(placeApplication2PlaceApplicationBO(placeApplication));
                }
            }
        } else {    // 不传入属性和值 则查询所有的场地申请信息
            List<PlaceApplication> placeApplications = placeApplicationDao.getAllPlaceApplications();
            // 如果placeApplications的长度大于0 则查出了所有的场地申请信息
            // 则将其组装为PlaceApplicationBO的List对象 用于在Controller中组装成为PlaceApplicationVO的对象 方便前端进行展示
            if (placeApplications.size() > 0) {
                for (PlaceApplication placeApplication : placeApplications) {
                    placeApplicationBOS.add(placeApplication2PlaceApplicationBO(placeApplication));
                }
            }
        }
        return placeApplicationBOS;
    }

    /**
     * 根据提交申请的时间区间和用户id查询对应的场地申请记录
     * @Date 2021/4/12 3:00
     * @param startDate 区间起始时间
     * @param endDate 区间结束时间
     * @param userId 用户ID
     * @return java.util.List<club.codemata.bo.PlaceApplicationBO>
     **/
    @Override
    public List<PlaceApplicationBO> getPlaceApplicationsByDate(String startDate, String endDate, String userId) throws Exception {
        List<PlaceApplicationBO> placeApplicationBOS = new ArrayList<>();
        List<PlaceApplication> placeApplications = placeApplicationDao.getPlaceApplicationsByApplicationDate(startDate, endDate, userId);
        // 如果placeApplications的长度大于0 则查出了相关的数据
        // 则将其组装为PlaceApplicationBO的List对象 用于在Controller中组装成为PlaceApplicationVO的对象 方便前端进行展示
        if (placeApplications.size() > 0) {
            for (PlaceApplication placeApplication : placeApplications) {
                placeApplicationBOS.add(placeApplication2PlaceApplicationBO(placeApplication));
            }
        }
        return placeApplicationBOS;
    }

    /**
     * 查出数据库中所有的场地申请记录 用来导出到excel文件
     * @Date 2021/4/15 13:42
     * @return java.util.List<club.codemata.bo.PlaceApplicationBO>
     **/
    @Override
    public List<PlaceApplicationBO> getAllPlaceApplications() throws Exception {
        List<PlaceApplicationBO> placeApplicationBOS = new ArrayList<>();
        List<PlaceApplication> placeApplications = placeApplicationDao.getAllPlaceApplications();
        if (placeApplications.size() > 0) {
            for (PlaceApplication application : placeApplications) {
                placeApplicationBOS.add(placeApplication2PlaceApplicationBO(application));
            }
        }
        return placeApplicationBOS;
    }

    /**
     * 根据属性及其对应值对场地申请记录进行计数<br>
     * 如果property和value都为空 那么则是对所有场地申请进行计数<br>
     * 查询结果将用于分页时的页数计算
     * @Date 2021/4/12 3:05
     * @param property 属性---场地编号,用户
     * @param value 属性对应值
     * @return int
     **/
    @Override
    public int count(String property, Object value) throws Exception {
        int res = 0;
        // 查询场地编号为value的场地申请记录数量 用于分页时进行页数计算
        if ("场地编号".equals(property)) {
            res = placeApplicationDao.count(property, value);
        } else if ("用户".equals(property)) { // 查询用户ID为value的场地申请记录数量 用于分页时进行页数计算
            res = placeApplicationDao.count(property, value);
        } else if ("使用状态".equals(property)) {
            res = placeApplicationDao.count(property, value);
        } else {    // 查询所有场地申请记录数量 用于分页时进行页数计算
            res = placeApplicationDao.count(property, value);
        }
        return res;
    }

    /**
     * 根据时间区间进行场地申请记录计数<br>
     * 查询结果将用于分页时的页数计算
     * @Date 2021/4/12 3:11
     * @param startDate
     * @param endDate
     * @return int
     **/
    @Override
    public int countByDate(String startDate, String endDate) throws Exception {
        return placeApplicationDao.countByDate(startDate, endDate);
    }

    /**
     * 计算需要支付的费用<br>
     * 计算方法:(startDate - endDate) * placePrice<br>
     * 如果是计算续租追加费用 那么startDate则是之前的结束时间 endDate则是续租后的结束时间
     * @Date 2021/4/12 3:32
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return int
     **/
    @Override
    public int calculatePaidCost(String startDate, String endDate, PlaceApplication application) throws Exception {
        // 查询对应的申请场地的费用
        int placePrice = placeInfoDao.getPlaceInfoByPlaceId(application.getPlaceId()).getPlacePrice();
        // 计算时间差
        int days = DateUtils.calculateDays(startDate, endDate);
        return days * placePrice;
    }

    /**
     * 将PlaceApplication对象转换为对应的PlaceApplicationBO对象<br>
     * 便于在Controller中组装中为PlaceApplicationVO的对象 用于前端展示
     * @Date 2021/4/12 2:28
     * @param application
     * @return club.codemata.bo.PlaceApplicationBO
     **/
    @Override
    public PlaceApplicationBO placeApplication2PlaceApplicationBO(PlaceApplication application) throws Exception {
        PlaceApplicationBO placeApplicationBO = new PlaceApplicationBO();
        // 查询申请用户的相关信息
        User user = userDao.getUserById(application.getApplicationUser());
        // 获取该申请对应的申请结果
        PlaceApplicationResult result = placeApplicationResultDao.getPlaceApplicationResultByResultId(application.getResultId());
        // 查找该申请记录是否被审核 如果被审核过 那么拿到审核该申请记录的管理员的ID
        // 根据管理员的ID查找管理员的身份信息 将被用于前端展示
        Manager manager = managerDao.getManagerById(result.getManagerId());
        // 如果manager等于空 则还没有管理员审核该申请 将管理员相关的所有信息设置为null
        if (manager == null) {
            placeApplicationBO.setManagerName(null);
            placeApplicationBO.setManagerTel(null);
        } else {
            placeApplicationBO.setManagerName(manager.getManagerName());
            placeApplicationBO.setManagerTel(manager.getManagerTel());
        }
        // 查询付款记录
        placeApplicationBO.setPayStatus(paymentDao.getPaymentByBillId(application.getApplicationId()) == null ? "未支付" : "已支付");
        // 组装其他信息
        placeApplicationBO.setApplicationId(application.getApplicationId());
        placeApplicationBO.setApplicationDate(application.getApplicationDate());
        placeApplicationBO.setApplicationReason(application.getApplicationReason());
        placeApplicationBO.setPlaceId(application.getPlaceId());
        placeApplicationBO.setCost(application.getCost());
        placeApplicationBO.setBeginDate(application.getBeginDate());
        placeApplicationBO.setEndDate(application.getEndDate());
        placeApplicationBO.setResult(result.getResult());
        placeApplicationBO.setResultDescription(result.getResultDescription());
        placeApplicationBO.setResultId(result.getResultId());
        placeApplicationBO.setUserName(user.getUserName());
        placeApplicationBO.setUserTel(user.getUserTel());
        placeApplicationBO.setResultDate(result.getResultDate());

        return placeApplicationBO;
    }
}
