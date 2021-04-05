package club.codemata.service.impl;

import club.codemata.bo.AnnouncementBO;
import club.codemata.dao.IAnnouncementDao;
import club.codemata.dao.IManagerDao;
import club.codemata.entity.Announcement;
import club.codemata.entity.Manager;
import club.codemata.service.IAnnouncementService;
import club.codemata.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName AnnouncementServiceImpl
 * @Description 公告信息业务层实现类
 * @createTime 2021/03/24 00:48:00
 */
@Service(value = "AnnouncementService")
public class AnnouncementServiceImpl implements IAnnouncementService {
    private IAnnouncementDao announcementDao;
    private IManagerDao managerDao;

    @Autowired
    @Qualifier(value = "IAnnouncementDao")
    public void setAnnouncementDao(IAnnouncementDao announcementDao) {
        this.announcementDao = announcementDao;
    }

    @Autowired
    @Qualifier(value = "IManagerDao")
    public void setManagerDao(IManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    /**
     * 添加一条新的公告
     * @Date 2021/4/4 17:49
     * @param title 新增公告的标题
     * @param content 新增公告的公告内容
     * @param author 新增公告的发布人
     * @param top 新增公告是否设置为置顶公告
     * @return int
     **/
    @Override
    public int saveAnnouncement(String title, String content, String author, String top) throws Exception {
        Announcement announcement = new Announcement();
        announcement.setId(UUIDUtils.getUUID());
        announcement.setTitle(title);
        announcement.setContent(content);
        announcement.setAuthor(author);
        announcement.setTop(top);
        // 如果新增公告也是置顶公告 则将数据库中的当前的置顶公告设置为非置顶
        if ("是".equals(top)) {
            announcementDao.updateTopStatus();
        }
        return announcementDao.saveAnnouncement(announcement);
    }

    /**
     * @author DengJie
     * @description 删除一条公告记录
     * @Date 2021/3/24 22:41
     * @Param [java.lang.String]
     * @return int
     */
    @Override
    public int removeAnnouncement(String id) throws Exception {
        return announcementDao.removeAnnouncement(id);
    }

    /**
     * 修改一条公告信息
     * 如果修改的公告为置顶公告 那么需要将数据库中当前的置顶公告修改为非置顶公告
     * @Date 2021/4/5 16:19
     * @param announcement 修改的公告信息
     * @return int
     **/
    @Override
    public int updateAnnouncement(Announcement announcement) throws Exception {
        // 如果更新公告也是置顶公告 则需要将数据库中当前的置顶公告修改为非置顶公告
        if ("是".equals(announcement.getTop())) {
            announcementDao.updateTopStatus();
        }
        return announcementDao.updateAnnouncement(announcement);
    }

    /**
     * 根据不同属性及其对应值进行查询
     * 当property == 标题时 进行模糊查询
     * @Date 2021/3/31 23:41
     * @param property 属性 --- 标题,发布人,日期
     * @param value 属性对应的值
     * @return java.util.List<club.codemata.bo.AnnouncementBO>
     **/
    @Override
    public List<AnnouncementBO> getAnnouncementsByKeyWords(String property, Object value) throws Exception {
        List<AnnouncementBO> announcementBOS = new ArrayList<>();
        // 根据不同的属性及其对应的值 进行查询
        // 如果property == 标题,则进行模糊查询 所以要对value进行拼接
        if ("标题".equals(property)) {
            if (value != null) {
                value = "%" + value + "%";
            }
            List<Announcement> announcements = announcementDao.getAnnouncementsByKeyWords(property, value);
            // 如果集合的场地大于0 则证明查出来了对应的数据 则将其封装为AnnouncementBO的集合 方便前端进行展示
            if (announcements.size() > 0) {
                for (Announcement announcement : announcements) {
                    announcementBOS.add(announcement2AnnouncementBO(announcement));
                }
            }
        } else if ("发布人".equals(property)) {
            List<Announcement> announcements = announcementDao.getAnnouncementsByKeyWords(property, value);
            if (announcements.size() > 0) {
                for (Announcement announcement : announcements) {
                    announcementBOS.add(announcement2AnnouncementBO(announcement));
                }
            }
        } else if ("日期".equals(property)) {
            List<Announcement> announcements = announcementDao.getAnnouncementsByKeyWords(property, value);
            if (announcements.size() > 0) {
                for (Announcement announcement : announcements) {
                    announcementBOS.add(announcement2AnnouncementBO(announcement));
                }
            }
        } else {
            // 如果传过来的参数中property和value都为空 则代表查询全部的公告
            List<Announcement> announcements = announcementDao.getAnnouncementsByKeyWords(null, null);
            for (Announcement announcement : announcements) {
                announcementBOS.add(announcement2AnnouncementBO(announcement));
            }
        }
        return announcementBOS;
    }

    /**
     * @author DengJie
     * @description 查询置顶的公告
     * @Date 2021/3/24 23:55
     * @Param []
     * @return club.codemata.bo.AnnouncementBO
     */
    @Override
    public List<AnnouncementBO> getTopAnnouncement() throws Exception {
        List<AnnouncementBO> announcementBOS = new ArrayList<>();
        Announcement announcement = announcementDao.getTopAnnouncement();
        // 将查询出来的数据封装为AnnouncementBO的列表 方便前端进行数据展示
        if (announcement != null) {
            announcementBOS.add(announcement2AnnouncementBO(announcement));
        }
        return announcementBOS;
    }

    /**
     * @author DengJie
     * @description 根据属性及其对应的值进行计数
     * @Date 2021/3/24 23:20
     * @Param [java.lang.String, java.lang.String]
     * @return int
     */
    @Override
    public int count(String property, String value) {
        if ("标题".equals(property)) {
            value = "%" + value + "%";
        }
        return announcementDao.count(property, value);
    }

    /**
     * @author DengJie
     * @description 将Announcement的对象封装为AnnouncementBO的对象
     * @Date 2021/3/24 23:20
     * @Param [club.codemata.entity.Announcement]
     * @return club.codemata.bo.AnnouncementBO
     */
    @Override
    public AnnouncementBO announcement2AnnouncementBO(Announcement announcement) {
        AnnouncementBO announcementBO = new AnnouncementBO();
        Manager manager = managerDao.getManagerById(announcement.getAuthor());
        announcementBO.setId(announcement.getId());
        announcementBO.setContent(announcement.getContent());
        announcementBO.setDate(announcement.getDate());
        announcementBO.setTop(announcement.getTop());
        announcementBO.setAuthorId(announcement.getAuthor());
        announcementBO.setAuthorName(manager.getManagerName());
        announcementBO.setAuthorTel(manager.getManagerTel());
        announcementBO.setTitle(announcement.getTitle());
        return announcementBO;
    }
}
