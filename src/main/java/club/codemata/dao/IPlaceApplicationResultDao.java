package club.codemata.dao;

import club.codemata.entity.PlaceApplicationResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IPlaceApplicationResultDao
 * @Description 场地申请处理结果数据库操作接口
 * @createTime 2021/04/12 00:07:00
 */
@Repository
public interface IPlaceApplicationResultDao {
    /**
     * 新增一条场地申请处理结果
     * @Date 2021/4/12 0:09
     * @param placeApplicationResult 场地申请处理结果
     * @return int
     **/
    public int savePlaceApplicationResult(PlaceApplicationResult placeApplicationResult) throws Exception;

    /**
     * 删除一条场地申请处理结果
     * @Date 2021/4/12 0:11
     * @param resultId 被删除的场地申请处理结果的ID
     * @return int
     **/
    public int removePlaceApplicationResult(@Param("resultId") String resultId) throws Exception;

    /**
     * 修改一条场地申请处理结果
     * @Date 2021/4/12 0:12
     * @param placeApplicationResult 修改后的场地申请处理结果
     * @return int
     **/
    public int updatePlaceApplicationResult(PlaceApplicationResult placeApplicationResult) throws Exception;

    /**
     * 修改一条申请结果的审核结果即是否同意使用
     * @Date 2021/4/14 23:41
     * @param result 管理员的审核结果
     * @param resultId 该条处理结果的ID
     * @return int
     **/
    public int updateResult(@Param("result") String result, @Param("resultId") String resultId) throws Exception;

    /**
     * 根据处理结果编号查询处理结果
     * @Date 2021/4/12 0:13
     * @param resultId 处理结果编号
     * @return club.codemata.entity.PlaceApplicationResult
     **/
    public PlaceApplicationResult getPlaceApplicationResultByResultId(@Param("resultId") String resultId) throws Exception;

    /**
     * 根据处理结果查询
     * @Date 2021/4/12 0:15
     * @param result 处理结果--通过,拒绝,待审核
     * @return java.util.List<club.codemata.entity.PlaceApplicationResult>
     **/
    public List<PlaceApplicationResult> getPlaceApplicationResultsByResult(@Param("result") String result) throws Exception;

    /**
     * 根据管理员ID查询该管理员审核通过的
     * @Date 2021/4/12 0:16
     * @param managerId 管理员ID
     * @return java.util.List<club.codemata.entity.PlaceApplicationResult>
     **/
    public List<PlaceApplicationResult> getPlaceApplicationResultsByManagerId(@Param("managerId") String managerId) throws Exception;

    /**
     * 根据属性及其对应值对处理结果进行计数
     * @Date 2021/4/12 0:24
     * @param property 属性--处理结果,管理员ID
     * @param value 属性对应的值
     * @return int
     **/
    public int count(@Param("property") String property, @Param("value") String value) throws Exception;
}
