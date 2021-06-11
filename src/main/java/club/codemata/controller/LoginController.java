package club.codemata.controller;

import club.codemata.dao.IManagerDao;
import club.codemata.service.IManagerService;
import club.codemata.service.IUserService;
import club.codemata.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LoginController
 * @Description 登录控制器
 * @createTime 2021/04/20 14:08:00
 */
@Controller
@RequestMapping(value = "/login/")
public class LoginController {
    private IManagerService managerService;
    private IUserService userService;

    @Autowired
    @Qualifier("ManagerService")
    public void setManagerService(IManagerService managerService) {
        this.managerService = managerService;
    }

    @Autowired
    @Qualifier("UserService")
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "managerLogin.do")
    @ResponseBody
    public MessageVO doManagerLogin(@RequestParam(value = "id") String id,
                                       @RequestParam(value = "password") String password,
                                       HttpServletRequest request) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = managerService.managerLogin(id, password);
            messageVO.setMsgId(res);
            if (res == 1) {
                messageVO.setMsgContent("登录成功");
                HttpSession session = request.getSession();
                session.setAttribute(id, true);
                session.setMaxInactiveInterval(60 * 30);
            } else {
                messageVO.setMsgContent("登录失败");
            }
        } catch (Exception e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("异常!");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "userLogin.do")
    @ResponseBody
    public MessageVO doUserLogin(@RequestParam(value = "userId") String userId,
                                 HttpServletRequest request) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = userService.countByUserId(userId);
            if (res > 0) {
                messageVO.setMsgId(1);
                messageVO.setMsgContent("成功");
                HttpSession session = request.getSession();
                session.setAttribute(userId, true);
            } else {
                messageVO.setMsgId(0);
                messageVO.setMsgContent("没有该用户");
            }
        } catch (Exception e) {
            messageVO.setMsgContent("服务端异常");
            messageVO.setMsgId(-1);
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "managerLogout.do")
    @ResponseBody
    public HashMap<String, Object> doManagerLogout(@RequestParam("managerId") String managerId,
                                                   HttpServletRequest request) {
        Object res = request.getSession().getAttribute(managerId);
        System.out.println("管理员登出:" + res);
        request.getSession().removeAttribute(managerId);
        HashMap<String, Object> info = new HashMap<>();
        info.put("status", "success");
        return info;
    }
}
