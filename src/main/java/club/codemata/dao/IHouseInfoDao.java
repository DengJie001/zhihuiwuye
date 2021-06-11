package club.codemata.dao;

import club.codemata.entity.HouseInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IHouseInfoDao
 * @Description 房产信息数据库操作接口
 * @createTime 2021/05/05 20:21:00
 */
@Repository
public interface IHouseInfoDao {
    public int saveHouseInfo(HouseInfo houseInfo) throws Exception;

    public int updateHouseInfo(HouseInfo houseInfo) throws Exception;

    public int removeHouseInfo(@Param("houseId") int houseId) throws Exception;

    public List<HouseInfo> getAllHouseInfos() throws Exception;

    public int count();
}
