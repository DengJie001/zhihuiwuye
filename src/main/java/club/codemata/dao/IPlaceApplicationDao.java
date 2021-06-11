package club.codemata.dao;

import club.codemata.entity.PlaceApplication;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IPlaceApplicationDao
 * @Description 场地申请数据库操作接口
 * @createTime 2021/04/09 17:48:00
 */
@Repository
public interface IPlaceApplicationDao {
    /**
     * 新增一条场地申请记录
     * @Date 2021/4/9 17:52
     * @param application 场地申请记录
     * @return int
     **/
    public int savePlaceApplication(PlaceApplication application) throws Exception;

    /**
     * 删除一条场地申请记录
     * @Date 2021/4/9 17:52
     * @param applicationId 需要被删除场地申请记录的ID
     * @return int
     **/
    public int removePlaceApplication(@Param("applicationId") String applicationId) throws Exception;

    /**
     * 修改一条场地申请记录
     * @Date 2021/4/9 17:53
     * @param application 修改后的场地申请记录
     * @return int
     **/
    public int updatePlaceApplication(PlaceApplication application) throws Exception;

    /**
     * 根据场地申请记录ID查询一条场地申请记录
     * @Date 2021/4/9 17:54
     * @param applicationId 需要查询场地申请的ID
     * @return int
     **/
    public PlaceApplication getPlaceApplicationById(@Param("applicationId") String applicationId) throws Exception;

    /**
     * 根据处理结果编号查询对应场地申请记录
     * @Date 2021/4/14 12:12
     * @param resultId 处理结果编号
     * @return java.util.List<club.codemata.entity.PlaceApplication>
     **/
    public PlaceApplication getPlaceApplicationByResultId(@Param("resultId") String resultId) throws Exception;

    /**
     * 根据场地编号查询对应的场地申请记录
     * @Date 2021/4/9 18:08
     * @param placeId 场地编号
     * @return int
     **/
    public List<PlaceApplication> getPlaceApplicationsByPlaceId(@Param("placeId") int placeId) throws Exception;

    /**
     * 根据用户ID查询场地申请记录
     * @Date 2021/4/9 18:10
     * @param userId 用户ID
     * @return java.util.List<club.codemata.entity.PlaceApplication>
     **/
    public List<PlaceApplication> getPlaceApplicationsByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 根据申请时间和用户ID查询场地申请记录
     * 如果userID为空则是查询所有在该时间范围内的场地申请记录
     * @Date 2021/4/9 18:12
     * @param startDate 起始时间
     * @param endDate 截至时间
     * @param userId 用户ID
     * @return java.util.List<club.codemata.entity.PlaceApplication>
     **/
    public List<PlaceApplication> getPlaceApplicationsByApplicationDate(@Param("startDate") String startDate,
                                                                        @Param("endDate") String endDate,
                                                                        @Param("userId") String userId) throws Exception;

    /**
     * 查询所有的场地申请记录
     * @Date 2021/4/12 2:57
     * @return java.util.List<club.codemata.entity.PlaceApplication>
     **/
    public List<PlaceApplication> getAllPlaceApplications() throws Exception;

    /**
     * 根据属性和值进行场地申请记录计数
     * @Date 2021/4/9 18:22
     * @param property 属性--场地编号,用户ID
     * @param value 属性对应值
     * @return int
     **/
    public int count(@Param("property") String property, @Param("value") Object value) throws Exception;

    /**
     * 通过起始时间对场地申请记录进行计数
     * @Date 2021/4/9 18:24
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return int
     **/
    public int countByDate(@Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;
}
