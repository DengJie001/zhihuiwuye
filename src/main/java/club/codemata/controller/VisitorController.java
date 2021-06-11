package club.codemata.controller;

import club.codemata.entity.Visitor;
import club.codemata.service.IVisitorService;
import club.codemata.vo.VisitorVO;
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
 * @ClassName VisitorController
 * @Description
 * @createTime 2021/05/08 17:46:00
 */
@Controller
@RequestMapping(value = "/visitor/")
public class VisitorController {
    private IVisitorService visitorService;

    @Autowired
    @Qualifier(value = "VisitorService")
    public void setVisitorService(IVisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @RequestMapping(value = "addVisitor.do")
    @ResponseBody
    public HashMap<String, Object> doAddVisitor(@RequestParam("visitorName") String visitorName,
                                                @RequestParam("visitorTel") String visitorTel,
                                                @RequestParam("visitorTemperature") float visitorTemperature,
                                                @RequestParam("visitorHomeAddress") String visitorHomeAddress,
                                                @RequestParam("visitorWorkAddress") String visiotrWorkAddress) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = visitorService.addVisitorInfo(visitorName, visitorTel, visitorTemperature, visitorHomeAddress, visiotrWorkAddress);
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

    @RequestMapping(value = "removeVisitor.do")
    @ResponseBody
    public HashMap<String, Object> doRemoveVisitor(@RequestParam("visitorId") String visitorId) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = visitorService.deleteVisitorInfo(visitorId);
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

    @RequestMapping(value = "getAllVisitors.do")
    @ResponseBody
    public HashMap<String, Object> doGetAllVisitors(@RequestParam("limit") int limit,
                                                    @RequestParam("page") int page) {
        HashMap<String, Object> info = new HashMap<>();
        VisitorVO vo = new VisitorVO();
        try {
            PageHelper.startPage(page, limit);
            List<Visitor> visitors = visitorService.getAllVisitors();
            PageInfo<Visitor> pageInfo = new PageInfo<>(visitors);
            vo.setVisitors(pageInfo.getList());
            vo.setCode(0);
            vo.setCount(visitorService.countAll());
            vo.setMsg("成功查询到" + vo.getCount() + "条记录!");
            info.put("status", "success");
            info.put("data", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "getVisitorsByTemperature.do")
    @ResponseBody
    public HashMap<String, Object> doGetVisitorsByTemperature(@RequestParam("limit") int limit,
                                                              @RequestParam("page") int page,
                                                              @RequestParam("visitorTemperature") float visitorTemperature) {
        HashMap<String, Object> info = new HashMap<>();
        VisitorVO vo = new VisitorVO();
        try {
            PageHelper.startPage(page, limit);
            List<Visitor> visitors = visitorService.getVisitorsByTemperature(visitorTemperature);
            PageInfo<Visitor> pageInfo = new PageInfo<>(visitors);
            vo.setVisitors(pageInfo.getList());
            vo.setCode(0);
            vo.setCount(visitorService.countByTemperature(visitorTemperature));
            vo.setMsg("成功查询到" + vo.getCount() + "条记录");
            info.put("status", "success");
            info.put("data", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "getVisitorsByDate.do")
    @ResponseBody
    public HashMap<String, Object> doGetVisitorsByDate(@RequestParam("limit") int limit,
                                                       @RequestParam("page") int page,
                                                       @RequestParam("visitDate") String visitDate) {
        HashMap<String, Object> info = new HashMap<>();
        VisitorVO vo = new VisitorVO();
        try {
            PageHelper.startPage(page, limit);
            List<Visitor> visitors = visitorService.getVisitorsByDate(visitDate);
            PageInfo<Visitor> pageInfo = new PageInfo<>(visitors);
            vo.setVisitors(pageInfo.getList());
            vo.setCode(0);
            vo.setCount(visitorService.countByDate(visitDate));
            vo.setMsg("成功查询到" + vo.getCount() + "条记录!");
            info.put("status", "success");
            info.put("data", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "exportAllVisitors.do")
    @ResponseBody
    public HashMap<String, Object> doExportAllVisitors() {
        HashMap<String, Object> info = new HashMap<>();
        VisitorVO vo = new VisitorVO();
        try {
            List<Visitor> visitors = visitorService.getAllVisitors();
            vo.setMsg("成功导出!");
            vo.setVisitors(visitors);
            vo.setCount(visitorService.countAll());
            vo.setCode(0);
            info.put("status", "success");
            info.put("data", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }
}
