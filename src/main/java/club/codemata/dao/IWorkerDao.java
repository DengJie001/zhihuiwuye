package club.codemata.dao;

import club.codemata.entity.Worker;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IWorkerDao
 * @Description 物业维修员工的数据库操作接口
 * @createTime 2021/04/15 16:24:00
 */
@Repository
public interface IWorkerDao {
    /**
     * 新增加一个物业维修人员
     * @Date 2021/4/15 16:25
     * @param worker 新增加的物业维修人员的基本信息
     * @return int
     **/
    public int saveWorker(Worker worker) throws Exception;

    /**
     * 删除一条物业维修人员的信息
     * @Date 2021/4/15 16:26
     * @param workerId 被删除人员的ID
     * @return int
     **/
    public int removeWorker(@Param("workerId") String workerId) throws Exception;

    /**
     * 更新物业维修人员的相关信息
     * @Date 2021/4/15 16:27
     * @param worker 修改后的信息
     * @return int
     **/
    public int updateWorker(Worker worker) throws Exception;

    /**
     * 增加一次维修人员的维修次数
     * @Date 2021/4/15 16:32
     * @param WorkerId 维修人员的ID
     * @return int
     **/
    public int updateWorkerTimes(@Param("workerId") String WorkerId, @Param("value") int value) throws Exception;

    /**
     * 增加一个等待人数<br>
     * 当用户选定该人员进行物业维修时 需要调用此方法 并且同时调用updateWorkerTimes
     * @Date 2021/4/15 16:33
     * @param workerId 维修人员ID
     * @return int
     **/
    public int updateWorkerWait(@Param("workerId") String workerId, @Param("value") int value) throws Exception;

    /**
     * 更新一个员工的总体评分
     * @Date 2021/4/22 23:06
     * @param workerId 员工ID
     * @param score 更新后的分数
     * @return int
     **/
    public int updateWorkerScore(@Param("workerId") String workerId, @Param("score") int score) throws Exception;

    /**
     * 根据ID查找一个维修人员的信息
     * @Date 2021/4/15 16:35
     * @param workerId 被查找的维修人员的ID
     * @return club.codemata.entity.Worker
     **/
    public Worker getWorkerByWorkerId(@Param("workerId") String workerId) throws Exception;

    /**
     * 查询评分低于score的维修人员
     * @Date 2021/4/15 22:11
     * @param score 评分
     * @return java.util.List<club.codemata.entity.Worker>
     **/
    public List<Worker> getWorkersByScore(@Param("score") float score) throws Exception;

    /**
     * 查询服务次数小于times的维修人员信息
     * @Date 2021/4/15 22:25
     * @param times 服务次数
     * @return java.util.List<club.codemata.entity.Worker>
     **/
    public List<Worker> getWorkersByTimes(@Param("times") int times) throws Exception;

    /**
     * 查询排队人数小于wait的维修人员信息
     * @Date 2021/4/15 22:26
     * @param wait 排队人数
     * @return java.util.List<club.codemata.entity.Worker>
     **/
    public List<Worker> getWorkersByWait(@Param("wait") int wait) throws Exception;

    /**
     * 根据维修人员简介的关键字搜索
     * @Date 2021/4/20 18:51
     * @param keyWords 关键字
     * @return java.util.List<club.codemata.entity.Worker>
     **/
    public List<Worker> getWorkersByDescription(@Param("keyWords") String keyWords) throws Exception;

    /**
     * 查询所有的维修人员信息
     * @Date 2021/4/15 22:11
     * @return java.util.List<club.codemata.entity.Worker>
     **/
    public List<Worker> getAllWorkers() throws Exception;

    /**
     * 根据属性及其对应值进行维修人员计数 <br>
     * 如果传入两个空参数 那么就是对所有的维修人员信息进行计数<br>
     * 将用于分页时进行页数计算<br>
     * @Date 2021/4/15 22:13
     * @param property 属性-评分,排队人数,服务次数
     * @param value 属性对应值
     * @return int
     **/
    public int count(@Param("property") String property, @Param("value") Object value) throws Exception;
}
