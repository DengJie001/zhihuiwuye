package club.codemata.service.impl;

import club.codemata.bo.HouseInfoBO;
import club.codemata.dao.IHouseInfoDao;
import club.codemata.dao.IUserDao;
import club.codemata.entity.HouseInfo;
import club.codemata.entity.User;
import club.codemata.service.IHouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName HouseInfoServiceImpl
 * @Description TODO
 * @createTime 2021/05/05 20:32:00
 */
@Service(value = "HouseInfoService")
public class HouseInfoServiceImpl implements IHouseInfoService {
    private IHouseInfoDao houseInfoDao;
    private IUserDao userDao;

    @Autowired
    @Qualifier("IHouseInfoDao")
    public void setHouseInfoDao(IHouseInfoDao houseInfoDao) {
        this.houseInfoDao = houseInfoDao;
    }

    @Autowired
    @Qualifier("IUserDao")
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 新增加一条房产信息
     * @Date 2021/5/5 20:33
     * @param areaId 该房产所处区号
     * @param unitId 该房产所处单元号
     * @param roomId 该房产门牌号
     * @return int
     **/
    @Override
    public int addHouseInfo(String areaId, String unitId, String roomId) throws Exception {
        HouseInfo houseInfo = new HouseInfo();
        houseInfo.setAreaId(areaId);
        houseInfo.setUnitId(unitId);
        houseInfo.setRoomId(roomId);
        return houseInfoDao.saveHouseInfo(houseInfo);
    }

    /**
     * 更新一条房产记录
     * @Date 2021/5/5 20:36
     * @param houseId 该房产的ID编号
     * @param areaId 修改后的区域号
     * @param unitId 修改后的单元号
     * @param roomId 修改后的门牌号
     * @return int
     **/
    @Override
    public int updateHouseInfo(int houseId, String areaId, String unitId, String roomId) throws Exception {
        HouseInfo houseInfo = new HouseInfo();
        houseInfo.setHouseId(houseId);
        houseInfo.setAreaId(areaId);
        houseInfo.setUnitId(unitId);
        houseInfo.setRoomId(roomId);
        return houseInfoDao.updateHouseInfo(houseInfo);
    }

    /**
     * 删除一条房产信息
     * @Date 2021/5/5 20:37
     * @param houseId 要删除的房产的ID编号
     * @return int
     **/
    @Override
    public int deleteHouseInfo(int houseId) throws Exception {
        return houseInfoDao.removeHouseInfo(houseId);
    }

    /**
     * 查询所有的房产信息
     * @Date 2021/5/5 20:44
     * @return java.util.List<club.codemata.bo.HouseInfoBO>
     **/
    @Override
    public List<HouseInfoBO> getAllHouseInfos() throws Exception {
        List<HouseInfoBO> bo = new ArrayList<>();
        List<HouseInfo> houseInfos = houseInfoDao.getAllHouseInfos();
        if (houseInfos.size() > 0) {
            for (HouseInfo houseInfo : houseInfos) {
                List<User> users = userDao.getUsersByAreaAndUnitAndRoom(houseInfo.getAreaId(), houseInfo.getUnitId(), houseInfo.getRoomId());
                if (users.size() > 0) {
                    for (User user : users) {
                        bo.add(houseInfo2HouseInfoBO(houseInfo, user));
                    }
                } else {
                    bo.add(houseInfo2HouseInfoBO(houseInfo, null));
                }
            }
        }
        return bo;
    }

    @Override
    public int count() throws Exception {
        return houseInfoDao.count();
    }

    @Override
    public HouseInfoBO houseInfo2HouseInfoBO(HouseInfo info, User user) throws Exception {
        HouseInfoBO bo = new HouseInfoBO();
        if (user == null) {
            bo.setUserName("暂无业主");
            bo.setUserTel("暂无业主");
        } else {
            bo.setUserName(user.getUserName());
            bo.setUserTel(user.getUserTel());
        }
        bo.setHouseId(info.getHouseId());
        bo.setAreaId(info.getAreaId());
        bo.setUnitId(info.getUnitId());
        bo.setRoomId(info.getRoomId());
        return bo;
    }
}
