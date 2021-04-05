package club.codemata.dao;

import club.codemata.entity.Announcement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IAnnouncementDao
 * @Description 公告信息数据库操作接口
 * @createTime 2021/03/23 14:45:00
 */
public interface IAnnouncementDao {
    /**
     * @author DengJie
     * @description 新增一条公告
     * @Date 2021/3/23 14:49
     * @Param [club.codemata.entity.Announcement]
     * @return int
     */
    public int saveAnnouncement(Announcement announcement);

    /**
     * @author DengJie
     * @description 根据公告id删除一条公告
     * @Date 2021/3/23 14:50
     * @Param [java.lang.String]
     * @return int
     */
    public int removeAnnouncement(@Param("id") String id);

    /**
     * @author DengJie
     * @description 修改一条公告记录
     * @Date 2021/3/23 14:50
     * @Param [club.codemata.entity.Announcement]
     * @return int
     */
    public int updateAnnouncement(Announcement announcement);

    /**
     * 修改置顶状态,即将数据库中现有的置顶公告的置顶状态修改为非置顶公告
     * @Date 2021/4/4 17:27
     * @return int
     **/
    public int updateTopStatus();

    /**
     * 按照标题,发布人ID,日期进行公告搜索,如果是根据标题搜索,那么则进行模糊查询
     * @Date 2021/3/29 19:08
     * @param property 按照哪个属性进行搜索,属性有:标题,发布人ID,日期
     * @param value 属性对应的值
     * @return java.util.List<club.codemata.entity.Announcement>
     **/
    public List<Announcement> getAnnouncementsByKeyWords(@Param("property") String property, @Param("value") Object value);

    /**
     * @author DengJie
     * @description 查找置顶的公告
     * @Date 2021/3/25 13:21
     * @Param []
     * @return club.codemata.entity.Announcement
     */
    public Announcement getTopAnnouncement();

    /**
     * @author DengJie
     * @description 根据传入的属性及其对应的值计数
     * @Date 2021/3/24 0:28
     * @Param [java.lang.String, java.lang.String]
     * @return int
     */
    public int count(@Param("property") String property, @Param("value") String value);
}