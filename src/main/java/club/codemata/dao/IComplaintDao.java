package club.codemata.dao;

import club.codemata.entity.Complaint;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IComplaintDao
 * @Description 用户投诉建议数据库操作接口
 * @createTime 2021/04/17 00:44:00
 */
@Repository
public interface IComplaintDao {
    /**
     * 新增加一条用户投诉建议
     * @Date 2021/4/17 0:44
     * @param complaint 投诉/建议
     * @return int
     **/
    public int saveComplaint(Complaint complaint) throws Exception;

    /**
     * 根据ID删除一条用户投诉/建议
     * @Date 2021/4/17 0:45
     * @param id 要删除的投诉/建议的ID
     * @return int
     **/
    public int removeComplaint(@Param("id") String id) throws Exception;

    /**
     * 更新投诉建议的处理状态
     * @Date 2021/4/17 15:26
     * @param id 被更新的投诉建议的ID
     * @param status 处理状态
     * @return int
     **/
    public int updateComplaintStatus(@Param("id") String id, @Param("status") String status) throws Exception;

    /**
     * 更新投诉/建议的用户意见
     * @Date 2021/4/17 15:26
     * @param id 被更新的投诉建议的ID
     * @param result 用户意见
     * @return int
     **/
    public int updateComplaintResult(@Param("id") String id, @Param("result") String result) throws Exception;

    /**
     * 更新管理员回复
     * @Date 2021/4/17 15:27
     * @param id 被更新的投诉建议的ID
     * @param managerId 回复该条投诉建议的管理员的ID
     * @param managerResponse 管理员回复的内容
     * @return int
     **/
    public int updateComplaintManagerResponse(@Param("id") String id,
                                              @Param("managerId") String managerId,
                                              @Param("managerResponse") String managerResponse,
                                              @Param("responseDate") String responseDate) throws Exception;

    /**
     * 根据投诉/建议的ID查找投诉/建议的信息
     * @Date 2021/4/17 0:46
     * @param id 要查找的投诉/建议的ID
     * @return club.codemata.entity.Complaint
     **/
    public Complaint getComplaintById(@Param("id") String id) throws Exception;

    /**
     * 查找所有的用户投诉/建议
     * @Date 2021/4/17 0:47
     * @return java.util.List<club.codemata.entity.Complaint>
     **/
    public List<Complaint> getAllComplaints() throws Exception;

    /**
     * 根据投诉/建议的处理状态查询
     * @Date 2021/4/17 0:53
     * @param status 处理状态
     * @return java.util.List<club.codemata.entity.Complaint>
     **/
    public List<Complaint> getComplaintsByStatus(@Param("status") String status) throws Exception;

    /**
     * 查询某个用户提交的所有投诉建议
     * @Date 2021/4/17 0:54
     * @param userId
     * @return java.util.List<club.codemata.entity.Complaint>
     **/
    public List<Complaint> getComplaintsByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 查询某个管理员处理的所有投诉/建议
     * @Date 2021/4/17 0:55
     * @param managerId
     * @return java.util.List<club.codemata.entity.Complaint>
     **/
    public List<Complaint> getComplaintsByManagerId(@Param("managerId") String managerId) throws Exception;

    /**
     * 根据分类(投诉/建议)查询
     * @Date 2021/4/17 0:56
     * @param category 分类
     * @return java.util.List<club.codemata.entity.Complaint>
     **/
    public List<Complaint> getComplaintsByCategory(@Param("category") String category) throws Exception;

    public List<Complaint> getComplaintsByResult(@Param("result") String result) throws Exception;

    /**
     * 根据属性及其对应值进行计数<br>
     * 计数结果将用于分页时的页数计算
     * @Date 2021/4/17 0:57
     * @param property 属性
     * @param value 属性对应值
     * @return int
     **/
    public int count(@Param("property") String property, @Param("value") String value) throws Exception;

    /**
     * 获取用户未读但是管理员已经回复的投诉/建议的数量
     * @Date 2021/4/19 20:05
     * @param userId 用户ID
     * @param result 一定为空
     * @param status 一定为已处理
     * @return int
     **/
    public int countUserUnreadComplaintReply(@Param("userId") String userId, @Param("result") String result, @Param("status") String status) throws Exception;
}
