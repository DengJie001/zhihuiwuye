package club.codemata.dao;

import club.codemata.entity.Announcement;
import club.codemata.utils.MybatisUtils;
import club.codemata.utils.UUIDUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;
import java.util.Random;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName AnnouncementDaoTest
 * @Description TODO
 * @createTime 2021/03/23 15:50:00
 */
public class AnnouncementDaoTest {
    @Test
    public void testSaveAnnouncement() {
        String[] authors = {
                "178206201", "178206203", "178206207", "178206216", "178206242", "18190129279"
        };
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        IAnnouncementDao mapper = sqlSession.getMapper(IAnnouncementDao.class);
        for (int i = 1; i <= 100; ++i) {
            Announcement announcement = new Announcement();
            announcement.setId(UUIDUtils.getUUID());
            announcement.setTitle("标题" + i);
            announcement.setContent("内容" + i);
            announcement.setAuthor(authors[new Random().nextInt(10) % 6]);
            announcement.setTop("否");

            mapper.saveAnnouncement(announcement);
        }
        sqlSession.close();
    }

    @Test
    public void testRemoveAnnouncement() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        IAnnouncementDao mapper = sqlSession.getMapper(IAnnouncementDao.class);
        int res = mapper.removeAnnouncement("fd0f580c-dd52-4ae9-931f-bcaaa76ea932");
        System.out.println(res);
        sqlSession.close();
    }

    @Test
    public void testUpdateAnnouncement() {
        Announcement announcement = new Announcement();
        announcement.setId("id1");
        announcement.setTitle("标题1");
        announcement.setContent("内容1");
        announcement.setAuthor("178206203");
        announcement.setTop("是");
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        IAnnouncementDao mapper = sqlSession.getMapper(IAnnouncementDao.class);
        mapper.updateAnnouncement(announcement);
    }

    @Test
    public void testGetAnnouncementsByKeyWords() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        IAnnouncementDao mapper = sqlSession.getMapper(IAnnouncementDao.class);
        List<Announcement> announcements = mapper.getAnnouncementsByKeyWords(null, null);
        for (Announcement announcement : announcements) {
            System.out.println(announcement);
        }
        sqlSession.close();
    }

    @Test
    public void testCount() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        IAnnouncementDao mapper = sqlSession.getMapper(IAnnouncementDao.class);
        int id = mapper.count("author", "178206201");
        System.out.println(id);
    }

    @Test
    public void testGetTopAnnouncement() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        IAnnouncementDao mapper = sqlSession.getMapper(IAnnouncementDao.class);
        Announcement announcement = mapper.getTopAnnouncement();
        System.out.println(announcement);
    }
}
