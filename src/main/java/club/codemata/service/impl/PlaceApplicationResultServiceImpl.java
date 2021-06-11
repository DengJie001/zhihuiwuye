package club.codemata.service.impl;

import club.codemata.dao.IPlaceApplicationResultDao;
import club.codemata.service.IPlaceApplicationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceApplicationResultServiceImpl
 * @Description TODO
 * @createTime 2021/04/14 12:50:00
 */
@Service(value = "PlaceApplicationResultService")
public class PlaceApplicationResultServiceImpl implements IPlaceApplicationResultService {
    private IPlaceApplicationResultDao placeApplicationResultDao;

    @Autowired
    @Qualifier("IPlaceApplicationResultDao")
    public void setPlaceApplicationResultDao(IPlaceApplicationResultDao placeApplicationResultDao) {
        this.placeApplicationResultDao = placeApplicationResultDao;
    }

    /**
     * 根据属性和值对处理结果进行计数
     * @Date 2021/4/14 12:50
     * @param property 属性
     * @param value 值
     * @return int
     **/
    @Override
    public int count(String property, Object value) throws Exception {
        int res = 0;
        if ("申请结果".equals(property)) {
            res = placeApplicationResultDao.count(property, value.toString());
        }
        return res;
    }
}
