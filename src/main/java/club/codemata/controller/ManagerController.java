package club.codemata.controller;

import club.codemata.entity.Manager;
import club.codemata.service.IManagerService;
import club.codemata.utils.ImageUtils;
import club.codemata.vo.ManagerVO;
import club.codemata.vo.MessageVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.media.jfxmediaimpl.HostUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ManagerController
 * @Description 管理员控制器
 * @createTime 2021/03/04 10:15:00
 */
@Controller
@RequestMapping(value = "/manager/")
public class ManagerController {
    private IManagerService managerService;

    @Autowired
    @Qualifier(value = "ManagerService")
    public void setManagerService(IManagerService managerService) {
        this.managerService = managerService;
    }

    @RequestMapping(value = "addManagerInfo.do")
    @ResponseBody
    public MessageVO addManagerInfo(@RequestParam("managerTel") String managerTel,
                                    @RequestParam("managerName") String managerName,
                                    @RequestParam("password") String password,
                                    @RequestParam("managerAvatar") String managerAvatar) {
        MessageVO messageVO = new MessageVO();
        Manager manager = new Manager();
        manager.setManagerTel(managerTel);
        manager.setManagerName(managerName);
        manager.setManagerAvatar(managerAvatar);
        manager.setPassword(password);

        int res = managerService.saveManagerInfo(manager);

        if (res > 0) {
            messageVO.setMsgId(1);
            messageVO.setMsgContent("新增成功");
        } else {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("操作未引起数据新增");
        }

        return messageVO;
    }

    @RequestMapping(value = "uploadManagerAvatar.do")
    @ResponseBody
    public String doTest(HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestParam("avatar") MultipartFile avatar) {
        String imgPath = null;
        try {
            imgPath = ImageUtils.upload(request, avatar, "管理员头像");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgPath;
    }

    @RequestMapping(value = "getAllManagers.do")
    @ResponseBody
    public ManagerVO doGetAllManagers(@RequestParam("limit") int limit, @RequestParam(required = false, defaultValue = "1") int page) {
        PageHelper.startPage(page, limit);
        ManagerVO managerVO = new ManagerVO();
        List<Manager> managers = managerService.getAllManagers();
        PageInfo<Manager> pageInfo = new PageInfo<>(managers);
        managerVO.setManagers(pageInfo.getList());
        managerVO.setCode(0);
        managerVO.setCount(managerService.countTotal(null, null));
        managerVO.setMsg("查询成功");
        return managerVO;
    }

    @RequestMapping(value = "removeManagerInfo.do")
    @ResponseBody
    public MessageVO doRemoveManagerInfo(@RequestParam("managerId") String managerId) {
        MessageVO msg = new MessageVO();
        int res = managerService.removeManagerInfo(managerId);
        if (res > 0) {
            msg.setMsgId(1);
            msg.setMsgContent("删除成功");
        } else {
            msg.setMsgId(-1);
            msg.setMsgContent("删除失败");
        }
        return msg;
    }

    @RequestMapping(value = "modifyManagerInfo.do")
    @ResponseBody
    public MessageVO doModifyManagerInfo(@RequestParam("managerId") String managerId,
                                         @RequestParam("managerTel") String managerTel,
                                         @RequestParam("managerName") String managerName) {
        MessageVO msg = new MessageVO();
        Manager manager = new Manager();
        manager.setManagerId(managerId);
        manager.setManagerTel(managerTel);
        manager.setManagerName(managerName);

        int res = managerService.updateManagerInfo(manager);

        if (res > 0) {
            msg.setMsgId(1);
            msg.setMsgContent("修改成功");
        } else {
            msg.setMsgId(-1);
            msg.setMsgContent("修改失败");
        }
        return msg;
    }

    @RequestMapping(value = "search.do")
    @ResponseBody
    public ManagerVO doSearch(@RequestParam("property") String property,
                              @RequestParam("keyWords") String keyWords,
                              @RequestParam("limit") int limit,
                              @RequestParam("page") int page
                              ) {
        ManagerVO managerVo = new ManagerVO();
        managerVo.setCode(0);
        PageHelper.startPage(page, limit);
        List<Manager> managers = managerService.search(property, keyWords);
        PageInfo<Manager> pageInfo = new PageInfo<>(managers);
        if (managers.size() > 0) {
            managerVo.setManagers(pageInfo.getList());
            managerVo.setCount(managerService.countTotal(property, keyWords));
            managerVo.setMsg("查询成功");
        } else {
            managerVo.setManagers(managers);
            managerVo.setCount(0);
            managerVo.setMsg("暂无数据");
        }
        return managerVo;
    }

    @RequestMapping(value = "exportAllInfos.do")
    @ResponseBody
    public List<Manager> doExportAllInfos() {
        List<Manager> managers = new ArrayList<>();
        managers = managerService.getAllManagers();
        return managers;
    }

    @RequestMapping(value = "getManagerInfo.do")
    @ResponseBody
    public HashMap<String, Object> doGetManagerInfo(@RequestParam("managerId") String managerId) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            Manager manager = managerService.getManagerById(managerId);
            manager.setPassword("******");
            info.put("data", manager);
            info.put("status", "success");
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "modifyManagerPassword.do")
    @ResponseBody
    public HashMap<String, Object> doModifyManagerPassword(@RequestParam("managerId") String managerId,
                                                           @RequestParam("password") String password) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = managerService.updateManagerPassword(managerId, password);
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
}
