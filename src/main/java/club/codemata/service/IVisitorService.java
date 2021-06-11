package club.codemata.service;

import club.codemata.entity.Visitor;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IVisitorService
 * @Description 来访人员登记业务层接口
 * @createTime 2021/05/08 17:33:00
 */
public interface IVisitorService {
    public int addVisitorInfo(String visitorName, String visitorTel, float visitorTemperature, String visitorHomeAddress, String visitorWorkAddress) throws Exception;

    public int deleteVisitorInfo(String visitorId) throws Exception;

    public List<Visitor> getAllVisitors() throws Exception;

    public List<Visitor> getVisitorsByTemperature(float visitorTemperature) throws Exception;

    public List<Visitor> getVisitorsByDate(String visitDate) throws Exception;

    public int countAll() throws Exception;

    public int countByTemperature(float temperature) throws Exception;

    public int countByDate(String visitDate) throws Exception;
}
