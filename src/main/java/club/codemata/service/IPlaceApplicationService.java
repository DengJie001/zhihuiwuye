package club.codemata.service;

import club.codemata.bo.PlaceApplicationBO;
import club.codemata.entity.PlaceApplication;

import java.util.HashMap;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IPlaceApplicationService
 * @Description 场地申请记录业务层接口
 * @createTime 2021/04/11 00:02:00
 */
public interface IPlaceApplicationService {
    public PlaceApplication savePlaceApplication(PlaceApplication application) throws Exception;

    public int removePlaceApplication(String applicationId) throws Exception;

    public int updatePlaceApplication(PlaceApplication application) throws Exception;

    public int updatePlaceApplicationResult(String applicationId, String result, String resultDescription, String managerId) throws Exception;

    public List<PlaceApplicationBO> getPlaceApplications(String property, Object value) throws Exception;

    public List<PlaceApplicationBO> getPlaceApplicationsByDate(String startDate, String endDate, String userId) throws Exception;

    public List<PlaceApplicationBO> getAllPlaceApplications() throws Exception;

    public int count(String property, Object value) throws Exception;

    public int countByDate(String startDate, String endDate) throws Exception;

    public int calculatePaidCost(String startDate, String endDate, PlaceApplication application) throws Exception;

    public PlaceApplicationBO placeApplication2PlaceApplicationBO(PlaceApplication application) throws Exception;
}
