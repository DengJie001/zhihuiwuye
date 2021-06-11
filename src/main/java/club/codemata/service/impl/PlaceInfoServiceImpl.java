package club.codemata.service.impl;

import club.codemata.bo.PlaceInfoBO;
import club.codemata.dao.IManagerDao;
import club.codemata.dao.IPlaceInfoDao;
import club.codemata.entity.Manager;
import club.codemata.entity.PlaceInfo;
import club.codemata.service.IPlaceInfoService;
import com.tencentcloudapi.cwp.v20180228.models.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceInfoServiceImpl
 * @Description 场地信息业务层逻辑
 * @createTime 2021/04/06 00:28:00
 */
@Service("PlaceInfoService")
public class PlaceInfoServiceImpl implements IPlaceInfoService {
    private IPlaceInfoDao placeInfoDao;
    private IManagerDao managerDao;

    @Autowired
    @Qualifier("IManagerDao")
    public void setManagerDao(IManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @Autowired
    @Qualifier("IPlaceInfoDao")
    public void setPlaceInfoDao(IPlaceInfoDao placeInfoDao) {
        this.placeInfoDao = placeInfoDao;
    }

    /**
     * 新增一条场地信息
     * @Date 2021/4/6 0:35
     * @param placeInfo 新增的场地信息
     * @return int
     **/
    @Override
    public int savePlaceInfo(PlaceInfo placeInfo) throws Exception {
        // 由于前端对于图片链接使用+进行简单拼接 在链接字符串中第一个字符一定是+
        // 如果图片链接不为空 将字符串中第一个+去除
        if (placeInfo.getPlacePicture() != null) {
            placeInfo.setPlacePicture(placeInfo.getPlacePicture().substring(1));
        }
        return placeInfoDao.savePlaceInfo(placeInfo);
    }

    /**
     * 根据场地编号删除一条场地信息
     * @Date 2021/4/6 0:48
     * @param placeId 需要删除的场地的编号
     * @return int
     **/
    @Override
    public int removePlaceInfo(int placeId) throws Exception {
        return placeInfoDao.removePlaceInfo(placeId);
    }

    /**
     * 更新一条场地信息
     * @Date 2021/4/6 0:49
     * @param placeInfo 更新的场地信息
     * @return int
     **/
    @Override
    public int updatePlaceInfo(PlaceInfo placeInfo) throws Exception {
        return placeInfoDao.updatePlaceInfo(placeInfo);
    }

    /**
     * 根据场地编号查询场地信息
     * @Date 2021/4/12 15:25
     * @param placeId 场地编号
     * @return int
     **/
    @Override
    public List<PlaceInfoBO> getPlaceInfoById(int placeId) throws Exception {
        List<PlaceInfoBO> placeInfoBOS = new ArrayList<>();
        PlaceInfo placeInfo = placeInfoDao.getPlaceInfoByPlaceId(placeId);
        // 将placeInfo转换为PlaceInfoBO的对象 将用于前端的展示
        if (placeInfo != null) {
            placeInfoBOS.add(placeInfo2PlaceInfoBO(placeInfo));
        }
        return placeInfoBOS;
    }

    /**
     * 根据对应属性及其值查询场地信息
     * @Date 2021/4/6 0:50
     * @param property 属性--场地编号,管理员ID,使用状态
     * @param value 属性对应的值
     * @return java.util.List<club.codemata.bo.PlaceInfoBO>
     **/
    @Override
    public List<PlaceInfoBO> getPlaceInfos(String property, Object value) throws Exception {
        List<PlaceInfoBO> placeInfoBOS = new ArrayList<>();
        // 根据不同属性执行查询操作
        // 属性为场地编号
        if ("场地编号".equals(property)) {
            PlaceInfo placeInfo = placeInfoDao.getPlaceInfoByPlaceId(value);
            PlaceInfoBO placeInfoBO = placeInfo2PlaceInfoBO(placeInfo);
            if (placeInfoBO == null) {
                // do nothing
            } else {
                placeInfoBOS.add(placeInfoBO);
            }
        } else if ("管理员".equals(property)) {    // 属性为管理员ID
            List<PlaceInfo> placeInfos = placeInfoDao.getPlaceInfosByManagerId(value.toString());
            // 如果placeInfos的长度大于0则将其封装为PlaceInfoBO的List集合 方便前端进行展示
            if (placeInfos.size() > 0) {
                for (PlaceInfo placeInfo : placeInfos) {
                    PlaceInfoBO placeInfoBO = placeInfo2PlaceInfoBO(placeInfo);
                    if (placeInfoBO == null) {
                        continue;
                    } else {
                        placeInfoBOS.add(placeInfoBO);
                    }
                }
            }
        } else if ("使用状态".equals(property)) {   // 属性为使用状态
            List<PlaceInfo> placeInfos = placeInfoDao.getPlaceInfosByPlaceStatus(value.toString());
            // 如果placeInfos的长度大于0则将其封装为PlaceInfoBO的List集合 方便前端进行展示
            if (placeInfos.size() > 0) {
                for (PlaceInfo placeInfo : placeInfos) {
                    PlaceInfoBO placeInfoBO = placeInfo2PlaceInfoBO(placeInfo);
                    if (placeInfoBO == null) {
                        continue;
                    } else {
                        placeInfoBOS.add(placeInfoBO);
                    }
                }
            }
        } else {    // 如果没有传入property 和 value则表示查询所有
            List<PlaceInfo> placeInfos = placeInfoDao.getAllPlaceInfos();
            // 如果placeInfos的长度大于0 则表示查出了所有的数据
            if (placeInfos.size() > 0) {
                for (PlaceInfo placeInfo : placeInfos) {
                    PlaceInfoBO placeInfoBO = placeInfo2PlaceInfoBO(placeInfo);
                    if (placeInfoBO == null) {
                        continue;
                    } else {
                        placeInfoBOS.add(placeInfoBO);
                    }
                }
            }
        }
        return placeInfoBOS;
    }

    /**
     * 根据属性的区间范围查询场地信息
     * @Date 2021/4/6 14:31
     * @param property 属性--面积,价格
     * @param lowerValue 区间最小值
     * @param higherValue 区间最大值
     * @return java.util.List<club.codemata.bo.PlaceInfoBO>
     **/
    @Override
    public List<PlaceInfoBO> getPlaceInfos(String property, int lowerValue, int higherValue) throws Exception {
        List<PlaceInfoBO> placeInfoBOS = new ArrayList<>();
        // 根据不同属性进行查询
        if ("面积".equals(property)) {
            List<PlaceInfo> placeInfos = placeInfoDao.getPlaceInfosByPlaceArea(lowerValue, higherValue);
            // 如果placeInfos的长度大于0 则代表查出了对应的数据 将其封装为PlaceInfoBO的List集合 方便前端进行展示
            System.out.println("结果" + placeInfos);
            if (placeInfos.size() > 0) {
                for (PlaceInfo placeInfo : placeInfos) {
                    PlaceInfoBO placeInfoBO = placeInfo2PlaceInfoBO(placeInfo);
                    if (placeInfoBO == null) {
                        continue;
                    } else {
                        placeInfoBOS.add(placeInfoBO);
                    }
                }
            }
        } else if ("价格".equals(property)) {
            List<PlaceInfo> placeInfos = placeInfoDao.getPlaceInfosByPlacePrice(lowerValue, higherValue);
            // 如果placeInfos的长度大于0 则代表在该价格区间范围内存在对应的场地信息
            // 将其封装为PlaceInfoBO的List集合 用于前端数据展示
            if (placeInfos.size() > 0) {
                for (PlaceInfo placeInfo : placeInfos) {
                    PlaceInfoBO placeInfoBO = placeInfo2PlaceInfoBO(placeInfo);
                    if (placeInfoBO == null) {
                        continue;
                    } else {
                        placeInfoBOS.add(placeInfoBO);
                    }
                }
            }
        }
        return placeInfoBOS;
    }

    /**
     * 根据属性及其对应值对场地信息进行计数
     * @Date 2021/4/6 14:37
     * @param property 属性--场地编号,管理员,使用状态
     * @param value 属性对应的值
     * @return int
     **/
    @Override
    public int count(String property, Object value) throws Exception {
        return placeInfoDao.countByOneValue(property, value);
    }

    /**
     * 根据属性对应的区间值对场地信息进行计数
     * @Date 2021/4/6 14:39
     * @param property 属性--价格,面积
     * @param lowerValue 区间最小值
     * @param higherValue 区间最大值
     * @return int
     **/
    @Override
    public int count(String property, int lowerValue, int higherValue) throws Exception {
        return placeInfoDao.countByTwoValue(property, lowerValue, higherValue);
    }

    /**
     * 将PlaceInfo的对象封装为PlaceInfoBO的对象
     * @Date 2021/4/6 1:15
     * @param placeInfo 需要进行封装的PlaceInfo的对象
     * @return club.codemata.bo.PlaceInfoBO
     **/
    public PlaceInfoBO placeInfo2PlaceInfoBO(PlaceInfo placeInfo) {
        if (placeInfo == null) {
            return null;
        }
        Manager manager = managerDao.getManagerById(placeInfo.getManagerId());
        PlaceInfoBO placeInfoBO = new PlaceInfoBO();
        placeInfoBO.setPlaceId(placeInfo.getPlaceId());
        placeInfoBO.setManagerId(placeInfo.getManagerId());
        placeInfoBO.setManagerName(manager.getManagerName());
        placeInfoBO.setManagerTel(manager.getManagerTel());
        placeInfoBO.setPlaceArea(placeInfo.getPlaceArea());
        placeInfoBO.setPlacePrice(placeInfo.getPlacePrice());
        placeInfoBO.setPlaceDescription(placeInfo.getPlaceDescription());
        placeInfoBO.setPlacePicture(placeInfo.getPlacePicture());
        placeInfoBO.setPlaceStatus(placeInfo.getPlaceStatus());
        return placeInfoBO;
    };
}
