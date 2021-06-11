package club.codemata.dao;

import club.codemata.entity.Visitor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IVisitorDao
 * @Description 来访人员数据库操作接口
 * @createTime 2021/05/08 17:12:00
 */
@Repository
public interface IVisitorDao {
    public int saveVisitorInfo(Visitor visitor) throws Exception;

    public int removeVisitorInfo(@Param("visitorId") String visitorId) throws Exception;

    public List<Visitor> getAllVisitors() throws Exception;

    public List<Visitor> getVisitorsByTemperature(@Param("visitorTemperature") float visitorTemperature) throws Exception;

    public List<Visitor> getVisitorByDate(@Param("visitDate") String visitDate) throws Exception;

    public int countAll() throws Exception;

    public int countByTemperature(@Param("temperature") float temperature) throws Exception;

    public int countByDate(@Param("visitDate") String visitDate) throws Exception;
}
