package club.codemata.service;

import club.codemata.bo.HouseInfoBO;
import club.codemata.entity.HouseInfo;
import club.codemata.entity.User;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IHouseInfoService
 * @Description 房产信息业务层接口
 * @createTime 2021/05/05 20:31:00
 */
public interface IHouseInfoService {
    public int addHouseInfo(String areaId, String unitId, String roomId) throws Exception;

    public int updateHouseInfo(int houseId, String areaId, String unitId, String roomId) throws Exception;

    public int deleteHouseInfo(int houseId) throws Exception;

    public List<HouseInfoBO> getAllHouseInfos() throws Exception;

    public int count() throws Exception;

    public HouseInfoBO houseInfo2HouseInfoBO(HouseInfo info, User user) throws Exception;
}
