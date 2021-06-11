package club.codemata.service.impl;

import club.codemata.dao.IVisitorDao;
import club.codemata.entity.Visitor;
import club.codemata.service.IVisitorService;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName VisitorServiceImpl
 * @Description 来访人员登记业务层逻辑
 * @createTime 2021/05/08 17:37:00
 */
@Service(value = "VisitorService")
public class VisitorServiceImpl implements IVisitorService {
    private IVisitorDao visitorDao;

    @Autowired
    @Qualifier(value = "IVisitorDao")
    public void setVisitorDao(IVisitorDao visitorDao) {
        this.visitorDao = visitorDao;
    }

    /**
     * 保存来访人员信息
     * @Date 2021/5/8 17:37
     * @param visitorName 来访人员姓名
     * @param visitorTel 来访人员联系方式
     * @param visitorTemperature 来访人员体温
     * @param visitorHomeAddress 来访人员的家庭住址
     * @param visitorWorkAddress 来访人员的工作地址
     * @return int
     **/
    @Override
    public int addVisitorInfo(String visitorName, String visitorTel, float visitorTemperature, String visitorHomeAddress, String visitorWorkAddress) throws Exception {
        Visitor visitor = new Visitor();
        visitor.setVisitorName(visitorName);
        visitor.setVisitorTel(visitorTel);
        visitor.setVisitorTemperature(visitorTemperature);
        visitor.setVisitorHomeAddress(visitorHomeAddress);
        visitor.setVisitorWorkAddress(visitorWorkAddress);
        return visitorDao.saveVisitorInfo(visitor);
    }

    /**
     * 删除一条来访人员信息
     * @Date 2021/5/8 17:41
     * @param visitorId 来访人员记录编号
     * @return int
     **/
    @Override
    public int deleteVisitorInfo(String visitorId) throws Exception {
        return visitorDao.removeVisitorInfo(visitorId);
    }

    /**
     * 查找所有来访人员信息
     * @Date 2021/5/8 17:42
     * @return java.util.List<club.codemata.entity.Visitor>
     **/
    @Override
    public List<Visitor> getAllVisitors() throws Exception {
        List<Visitor> visitors = visitorDao.getAllVisitors();
        return visitors;
    }

    /**
     * 查找体温高于参数的来访人员记录
     * @Date 2021/5/8 17:43
     * @param visitorTemperature 体温
     * @return java.util.List<club.codemata.entity.Visitor>
     **/
    @Override
    public List<Visitor> getVisitorsByTemperature(float visitorTemperature) throws Exception {
        List<Visitor> visitors = visitorDao.getVisitorsByTemperature(visitorTemperature);
        return visitors;
    }

    /**
     * 查找参数当天的所有来访人员记录
     * @Date 2021/5/8 17:44
     * @param visitDate 日期
     * @return java.util.List<club.codemata.entity.Visitor>
     **/
    @Override
    public List<Visitor> getVisitorsByDate(String visitDate) throws Exception {
        List<Visitor> visitors = visitorDao.getVisitorByDate(visitDate);
        return visitors;
    }


    @Override
    public int countAll() throws Exception {
        return visitorDao.countAll();
    }

    @Override
    public int countByTemperature(float temperature) throws Exception {
        return visitorDao.countByTemperature(temperature);
    }

    @Override
    public int countByDate(String visitDate) throws Exception {
        return visitorDao.countByDate(visitDate);
    }
}
