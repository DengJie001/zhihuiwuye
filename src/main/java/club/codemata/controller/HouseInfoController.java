package club.codemata.controller;

import club.codemata.bo.HouseInfoBO;
import club.codemata.entity.HouseInfo;
import club.codemata.service.IHouseInfoService;
import club.codemata.vo.HouseInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName HouseInfoController
 * @Description 房产信息请求控制器
 * @createTime 2021/05/05 21:02:00
 */
@Controller
@RequestMapping(value = "/HouseInfo/")
public class HouseInfoController {
    private IHouseInfoService houseService;

    @Autowired
    @Qualifier("HouseInfoService")
    public void setHouseService(IHouseInfoService houseService) {
        this.houseService = houseService;
    }

    @RequestMapping(value = "addHouseInfo.do")
    @ResponseBody
    public HashMap<String, Object> doAddHouseInfo(@RequestParam("areaId") String areaId,
                                                  @RequestParam("unitId") String unitId,
                                                  @RequestParam("roomId") String roomId) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = houseService.addHouseInfo(areaId, unitId, roomId);
            if (res > 0) {
                info.put("status", "success");
            } else {
                info.put("status", "error");
            }
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "modifyHouseInfo.do")
    @ResponseBody
    public HashMap<String, Object> doModifyHouseInfo(@RequestParam("houseId") int houseId,
                                                     @RequestParam("areaId") String areaId,
                                                     @RequestParam("unitId") String unitId,
                                                     @RequestParam("roomId") String roomId) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = houseService.updateHouseInfo(houseId, areaId, unitId, roomId);
            if (res > 0) {
                info.put("status", "success");
            } else {
                info.put("status", "error");
            }
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "deleteHouseInfo.do")
    @ResponseBody
    public HashMap<String, Object> doDeleteHouseInfo(@RequestParam("houseId") int houseId) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = houseService.deleteHouseInfo(houseId);
            if (res > 0) {
                info.put("status", "success");
            } else {
                info.put("status", "error");
            }
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "getAllHouseInfos.do")
    @ResponseBody
    public HashMap<String, Object> doGetAllHouseInfos(@RequestParam("limit") int limit,
                                                      @RequestParam("page") int page) {
        HashMap<String, Object> info = new HashMap<>();
        HouseInfoVO vo = new HouseInfoVO();
        try {
            PageHelper.startPage(page, limit);
            List<HouseInfoBO> houseInfos = houseService.getAllHouseInfos();
            PageInfo<HouseInfoBO> pageInfo = new PageInfo<>(houseInfos);
            vo.setHouseInfos(pageInfo.getList());
            vo.setCode(0);
            vo.setCount(houseService.count());
            vo.setMsg("成功查询到" + vo.getCount() + "条记录!");
            info.put("status", "success");
            info.put("data", vo);
        } catch (Exception e) {
            vo.setMsg("服务端异常");
            vo.setCode(-1);
            vo.setCount(0);
            info.put("status", "exception");
            info.put("data", vo);
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "exportAllHouseInfos.do")
    @ResponseBody
    public HashMap<String, Object> doExportAllHouseInfos() {
        HashMap<String, Object> info = new HashMap<>();
        HouseInfoVO vo = new HouseInfoVO();
        try {
            List<HouseInfoBO> houseInfos = houseService.getAllHouseInfos();
            vo.setCount(houseService.count());
            vo.setCode(0);
            vo.setHouseInfos(houseInfos);
            vo.setMsg("导出" + vo.getCount() + "条记录!");
            info.put("status", "success");
            info.put("data", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "userGetAllHouseInfos.do")
    @ResponseBody
    public HashMap<String, Object> doUserGetAllHouseInfos() {
        HashMap<String, Object> info = new HashMap<>();
        try {
            List<HouseInfoBO> houseInfos = houseService.getAllHouseInfos();
            info.put("status", "success");
            info.put("info", houseInfos);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }
}
