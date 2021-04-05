package club.codemata.service;

import club.codemata.bo.AnnouncementBO;
import club.codemata.entity.Announcement;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IAnnouncementService
 * @Description 公告信息业务层接口
 * @createTime 2021/03/24 00:34:00
 */
public interface IAnnouncementService {
    public int saveAnnouncement(String title, String content, String author, String top) throws Exception;

    public int removeAnnouncement(String id) throws Exception;

    public int updateAnnouncement(Announcement announcement) throws Exception;

    public List<AnnouncementBO> getAnnouncementsByKeyWords(String property, Object value) throws Exception;

    public List<AnnouncementBO> getTopAnnouncement() throws Exception;

    public int count(String property, String value);

    public AnnouncementBO announcement2AnnouncementBO(Announcement announcement);
}
