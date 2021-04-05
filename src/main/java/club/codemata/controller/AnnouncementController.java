package club.codemata.controller;

import club.codemata.bo.AnnouncementBO;
import club.codemata.entity.Announcement;
import club.codemata.service.IAnnouncementService;
import club.codemata.utils.UUIDUtils;
import club.codemata.vo.AnnouncementVO;
import club.codemata.vo.MessageVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName AnnouncementController
 * @Description 公告信息控制器
 * @createTime 2021/03/24 23:21:00
 */
@Controller
@RequestMapping(value = "/announcement/")
public class AnnouncementController {
    private IAnnouncementService announcementService;

    @Autowired
    @Qualifier(value = "AnnouncementService")
    public void setAnnouncementService(IAnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @RequestMapping(value = "saveAnnouncement.do")
    @ResponseBody
    public MessageVO doAddAnnouncement(@RequestParam("title") String title,
                                       @RequestParam("content") String content,
                                       @RequestParam("author") String author,
                                       @RequestParam("top") String top) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = announcementService.saveAnnouncement(title, content, author, top);
            messageVO.setMsgId(res);
            messageVO.setMsgContent("新增了" + res + "条公告");
        } catch (Exception exception) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("发生了异常!");
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "getAnnouncements.do")
    @ResponseBody
    public AnnouncementVO doGetAnnouncements(@RequestParam(required = false, value = "page") int page,
                                             @RequestParam(required = false, value = "limit") int limit,
                                             @RequestParam(required = false, value = "property") String property,
                                             @RequestParam(required = false, value = "value") String value) {
        AnnouncementVO announcementVO = new AnnouncementVO();
        try {
            System.out.println("property:" + property);
            System.out.println("value:" + value);
            if (property != null && value != null) {
                announcementVO.setCount(announcementService.count(property, value));
            } else {
                announcementVO.setCount(announcementService.count(null, null));
            }
            PageHelper.startPage(page, limit);
            List<AnnouncementBO> announcements = announcementService.getAnnouncementsByKeyWords(property, value);
            PageInfo<AnnouncementBO> pageInfo = new PageInfo<>(announcements);
            announcementVO.setAnnouncements(pageInfo.getList());
            announcementVO.setMsg("查询成功");
        } catch (Exception exception) {
            announcementVO.setCode(-1);
            announcementVO.setMsg("发生了异常");
            announcementVO.setCount(0);
            exception.printStackTrace();
        } finally {
            return announcementVO;
        }
    }

    @RequestMapping(value = "getTopAnnouncement.do")
    @ResponseBody
    public AnnouncementVO doGetTopAnnouncement() {
        AnnouncementVO announcementVO = new AnnouncementVO();
        try {
            List<AnnouncementBO> announcements = announcementService.getTopAnnouncement();
            announcementVO.setAnnouncements(announcements);
            announcementVO.setCount(announcementVO.getAnnouncements().size());
            announcementVO.setCode(0);
            announcementVO.setMsg("查询置顶公告成功");
        } catch (Exception exception) {
            announcementVO.setMsg("出现了异常");
            announcementVO.setCode(-1);
            announcementVO.setCount(0);
        }
        return announcementVO;
    }

    @RequestMapping("exportAllAnnouncements.do")
    @ResponseBody
    public AnnouncementVO doExportAllAnnouncements() {
        AnnouncementVO announcementVO = new AnnouncementVO();
        try {
            List<AnnouncementBO> announcements = announcementService.getAnnouncementsByKeyWords(null, null);
            announcementVO.setAnnouncements(announcements);
            announcementVO.setMsg("查询成功");
            announcementVO.setCode(0);
            announcementVO.setCount(announcementService.count(null, null));
        } catch (Exception exception) {
            announcementVO.setMsg("发生了异常");
        } finally {
            return announcementVO;
        }
    }

    @RequestMapping("removeAnnouncement.do")
    @ResponseBody
    public MessageVO doRemoveAnnouncement(@Param("id") String id) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = announcementService.removeAnnouncement(id);
            messageVO.setMsgId(res);
            messageVO.setMsgContent("操作成功");
        } catch (Exception exception) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("发生了异常");
        } finally {
            return messageVO;
        }
    }

    @RequestMapping("modifyAnnouncement.do")
    @ResponseBody
    public MessageVO doModifyAnnouncement(@Param("announcement") String announcement) {
        System.out.println(announcement);
        MessageVO messageVO = new MessageVO();
        Announcement announcement1 = JSON.parseObject(announcement, Announcement.class);
        System.out.println(announcement1);
        try {
            int res = announcementService.updateAnnouncement(announcement1);
            messageVO.setMsgId(res);
            if (res > 0) {
                messageVO.setMsgContent("成功更新了" + res + "条公告信息");
            } else if (res == 0) {
                messageVO.setMsgContent("操作成功,但未更新数据");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("发生了异常");
        } finally {
            return messageVO;
        }
    }
}
