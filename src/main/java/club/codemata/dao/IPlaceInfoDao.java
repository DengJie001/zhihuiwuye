package club.codemata.dao;

import club.codemata.entity.PlaceInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IPlaceInfoDao
 * @Description 场地信息数据库操作接口
 * @createTime 2021/04/05 21:59:00
 */
@Repository
public interface IPlaceInfoDao {
    /**
     * 新增一条场地信息
     * @Date 2021/4/5 22:00
     * @param placeInfo 新增的场地信息
     * @return int
     **/
    public int savePlaceInfo(PlaceInfo placeInfo) throws Exception;

    /**
     * 根据场地编号删除一条场地信息
     * @Date 2021/4/5 22:02
     * @param placeId 要删除的场地信息的编号
     * @return int
     **/
    public int removePlaceInfo(@Param("placeId") int placeId) throws Exception;

    /**
     * 修改场地信息
     * @Date 2021/4/5 22:03
     * @param placeInfo 修改后的场地信息
     * @return int
     **/
    public int updatePlaceInfo(PlaceInfo placeInfo) throws Exception;

    /**
     * 修改对应场地的使用状态
     * @Date 2021/4/12 15:15
     * @param placeId 需要修改场地的编号
     * @param placeStatus 修改后的状态
     * @return int
     **/
    public int updatePlaceStatus(@Param("placeId") int placeId, @Param("placeStatus") String placeStatus) throws Exception;

    /**
     * 根据场地编号查询一条场地信息
     * @Date 2021/4/5 22:08
     * @param placeId 场地信息编号
     * @return club.codemata.entity.PlaceInfo
     **/
    public PlaceInfo getPlaceInfoByPlaceId(@Param("placeId") Object placeId) throws Exception;

    /**
     * 查询对应管理员所管理的所有场地信息
     * @Date 2021/4/5 22:13
     * @param managerId 管理员ID
     * @return java.util.List<club.codemata.entity.PlaceInfo>
     **/
    public List<PlaceInfo> getPlaceInfosByManagerId(@Param("managerId") String managerId) throws Exception;

    /**
     * 根据场地使用面积区间查询场地信息
     * @Date 2021/4/5 22:14
     * @param lowerArea 场地最小面积
     * @param higherArea 场地最大面积
     * @return java.util.List<club.codemata.entity.PlaceInfo>
     **/
    public List<PlaceInfo> getPlaceInfosByPlaceArea(@Param("lowerArea") int lowerArea,
                                                    @Param("higherArea") int higherArea) throws Exception;

    /**
     * 根据场地的使用价格区间查询场地信息
     * @Date 2021/4/5 22:24
     * @param lowerPrice 租用场地最低价格
     * @param higherPrice 租用场地最高价格
     * @return java.util.List<club.codemata.entity.PlaceInfo>
     **/
    public List<PlaceInfo> getPlaceInfosByPlacePrice(@Param("lowerPrice") int lowerPrice,
                                                     @Param("higherPrice") int higherPrice) throws Exception;

    /**
     * 通过场地的使用状态查询场地信息
     * @Date 2021/4/5 22:27
     * @param placeStatus 场地使用状态(使用中,未使用,维护中)
     * @return java.util.List<club.codemata.entity.PlaceInfo>
     **/
    public List<PlaceInfo> getPlaceInfosByPlaceStatus(@Param("placeStatus") String placeStatus) throws Exception;

    /**
     * 查询所有的场地信息
     * @Date 2021/4/6 16:51
     * @return java.util.List<club.codemata.entity.PlaceInfo>
     **/
    public List<PlaceInfo> getAllPlaceInfos() throws Exception;

    /**
     * 通过一个属性的一个值对场地信息进行计数
     * @Date 2021/4/5 22:31
     * @param property 属性
     * @param value 属性对应的值
     * @return int
     **/
    public int countByOneValue(@Param("property") String property,@Param("value") Object value) throws Exception;

    /**
     * 通过某个属性对应的区间值对场地信息进行计数
     * @Date 2021/4/5 22:32
     * @param property 属性
     * @param lowerValue 区间最小值
     * @param higherValue 区间最大值
     * @return int
     **/
    public int countByTwoValue(@Param("property") String property,
                               @Param("lowerValue") int lowerValue,
                               @Param("higherValue") int higherValue) throws Exception;
}
