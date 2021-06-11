package club.codemata.service;

import club.codemata.bo.PlaceInfoBO;
import club.codemata.entity.PlaceInfo;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IPlaceInfoService
 * @Description 场地信息业务层接口
 * @createTime 2021/04/05 23:45:00
 */
public interface IPlaceInfoService {
    public int savePlaceInfo(PlaceInfo placeInfo) throws Exception;

    public int removePlaceInfo(int placeId) throws Exception;

    public int updatePlaceInfo(PlaceInfo placeInfo) throws Exception;

    public List<PlaceInfoBO> getPlaceInfoById(int placeId) throws Exception;

    public List<PlaceInfoBO> getPlaceInfos(String property, Object value) throws Exception;

    public List<PlaceInfoBO> getPlaceInfos(String property, int lowerValue, int higherValue) throws Exception;

    public int count(String property, Object value) throws Exception;

    public int count(String property, int lowerValue, int higherValue) throws Exception;
}
