package club.codemata.controller;

import club.codemata.bo.PlaceApplicationBO;
import club.codemata.entity.PlaceApplication;
import club.codemata.service.IPlaceApplicationResultService;
import club.codemata.service.IPlaceApplicationService;
import club.codemata.vo.MessageVO;
import club.codemata.vo.PlaceApplicationVO;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceApplicationController
 * @Description 场地申请控制器
 * @createTime 2021/04/12 03:40:00
 */
@Controller
@RequestMapping(value = "/PlaceApplication/")
public class PlaceApplicationController {
    private IPlaceApplicationService placeApplicationService;
    private IPlaceApplicationResultService placeApplicationResultService;

    @Autowired
    @Qualifier("PlaceApplicationService")
    public void setPlaceApplicationService(IPlaceApplicationService placeApplicationService) {
        this.placeApplicationService = placeApplicationService;
    }

    @Autowired
    @Qualifier("PlaceApplicationResultService")
    public void setPlaceApplicationResultService(IPlaceApplicationResultService placeApplicationResultService) {
        this.placeApplicationResultService = placeApplicationResultService;
    }

    @RequestMapping(value = "savePlaceApplication.do")
    @ResponseBody
    public HashMap<String, Object> doSavePlaceApplication(@RequestParam("placeApplication") String placeApplication) {
        MessageVO messageVO = new MessageVO();
        PlaceApplication application = JSON.parseObject(placeApplication, PlaceApplication.class);
        HashMap<String, Object> data = new HashMap<>();
        try {
            PlaceApplication res = placeApplicationService.savePlaceApplication(application);
            if (res != null) {
                data.put("status", "success");
                data.put("data", res);
            } else {
                data.put("status", "error");
            }
        } catch (Exception e) {
            data.put("status", "exception");
            e.printStackTrace();
        } finally {
            return data;
        }
    }

    @RequestMapping(value = "getPlaceApplications.do")
    @ResponseBody
    public PlaceApplicationVO doGetPlaceApplications(@RequestParam(value = "page", required = false) int page,
                                                     @RequestParam(value = "limit", required = false) int limit,
                                                     @RequestParam(value = "property", required = false) String property,
                                                     @RequestParam(value = "value", required = false) Object value) {
        System.out.println("property:" + property);
        System.out.println("value:" + value);
        PlaceApplicationVO placeApplicationVO = new PlaceApplicationVO();
        try {
            PageHelper.startPage(page, limit);
            List<PlaceApplicationBO> placeApplications = placeApplicationService.getPlaceApplications(property, value);
            PageInfo<PlaceApplicationBO> pageInfo = new PageInfo<>(placeApplications);
            placeApplicationVO.setPlaceApplications(pageInfo.getList());
            System.out.println(placeApplicationVO.getPlaceApplications().size());
            placeApplicationVO.setCode(0);
            placeApplicationVO.setCount(placeApplicationService.count(property, value));
            placeApplicationVO.setMsg("成功查询到" + placeApplicationVO.getCount() + "条记录!");
        } catch (Exception e) {
            placeApplicationVO.setCode(-1);
            placeApplicationVO.setCount(0);
            placeApplicationVO.setMsg("发生了异常");
            e.printStackTrace();
        } finally {
            return placeApplicationVO;
        }
    }

    @RequestMapping(value = "getPlaceApplicationsByResult.do")
    @ResponseBody
    public PlaceApplicationVO doGetPlaceApplicationsByResult(@RequestParam(value = "page", required = false) int page,
                                                             @RequestParam(value = "limit", required = false) int limit,
                                                             @RequestParam(value = "property", required = false) String property,
                                                             @RequestParam(value = "value", required = false) String value) {
        PlaceApplicationVO placeApplicationVO = new PlaceApplicationVO();
        try {
            PageHelper.startPage(page, limit);
            List<PlaceApplicationBO> placeApplications = placeApplicationService.getPlaceApplications(property, value);
            PageInfo<PlaceApplicationBO> pageInfo = new PageInfo<>(placeApplications);
            placeApplicationVO.setPlaceApplications(pageInfo.getList());
            placeApplicationVO.setCode(0);
            placeApplicationVO.setCount(placeApplicationResultService.count(property, value));
            placeApplicationVO.setMsg("成功查询到" + placeApplicationVO.getCount() + "条消息");
        } catch (Exception e) {
            placeApplicationVO.setCode(-1);
            placeApplicationVO.setCount(0);
            placeApplicationVO.setMsg("发生了异常");
            e.printStackTrace();
        } finally {
            return placeApplicationVO;
        }
    }

    @RequestMapping(value = "modifyApplicationResult.do")
    @ResponseBody
    public MessageVO doModifyApplicationResult(@RequestParam(value = "applicationId") String applicationId,
                                               @RequestParam(value = "result") String result,
                                               @RequestParam(value = "resultDescription") String resultDescription,
                                               @RequestParam(value = "managerId") String managerId) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = placeApplicationService.updatePlaceApplicationResult(applicationId, result, resultDescription, managerId);
            if (res > 0) {
                messageVO.setMsgId(res);
                messageVO.setMsgContent("操作成功");
            } else if (res == -1) {
                messageVO.setMsgId(res);
                messageVO.setMsgContent("没有这条申请记录,用户可能已经删除!");
            } else {
                messageVO.setMsgId(0);
                messageVO.setMsgContent("操作成功,但未引起数据变化!");
            }
        } catch (Exception e) {
            messageVO.setMsgId(-2);
            messageVO.setMsgContent("操作时发生异常");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "exportAllPlaceApplications.do")
    @ResponseBody
    public PlaceApplicationVO doExportAllPlaceApplications() {
        PlaceApplicationVO placeApplicationVO = new PlaceApplicationVO();
        try {
            List<PlaceApplicationBO> placeApplications = placeApplicationService.getAllPlaceApplications();
            placeApplicationVO.setPlaceApplications(placeApplications);
            placeApplicationVO.setCode(0);
            placeApplicationVO.setCount(placeApplicationService.count(null, null));
            placeApplicationVO.setMsg("导出成功");
        } catch (Exception e) {
            placeApplicationVO.setCode(-1);
            placeApplicationVO.setCount(0);
            placeApplicationVO.setMsg("发生了异常");
            e.printStackTrace();
        } finally {
            return placeApplicationVO;
        }
    }

    @RequestMapping(value = "userGetApplications.do")
    @ResponseBody
    public HashMap<String, Object> doUserGetApplications(@RequestParam(value = "userId") String userId,
                                                         @RequestParam(value = "page") int page,
                                                         @RequestParam(value = "limit") int limit) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            PlaceApplicationVO applicationVO = new PlaceApplicationVO();
            PageHelper.startPage(page, limit);
            List<PlaceApplicationBO> applications = placeApplicationService.getPlaceApplications("用户", userId);
            PageInfo<PlaceApplicationBO> pageInfo =new PageInfo<>(applications);
            applicationVO.setPlaceApplications(pageInfo.getList());
            applicationVO.setMsg("查询成功");
            applicationVO.setCount(placeApplicationService.count("用户", userId));
            applicationVO.setCode(0);
            res.put("status", "success");
            res.put("info", applicationVO);
        } catch (Exception e) {
            res.put("status", "exception");
            e.printStackTrace();
        } finally {
            return res;
        }
    }

    @RequestMapping(value = "userDeleteApplication.do")
    @ResponseBody
    public HashMap<String, Object> doUserDeleteApplication(@RequestParam(value = "applicationId") String applicationId) {
        HashMap<String, Object> resHash = new HashMap<>();
        try {
            int result = placeApplicationService.removePlaceApplication(applicationId);
            if (result > 0) {
                resHash.put("status", "success");
            } else {
                resHash.put("status", "error");
            }
        } catch (Exception e) {
            resHash.put("status", "exception");
            e.printStackTrace();
        } finally {
            return resHash;
        }
    }
}
