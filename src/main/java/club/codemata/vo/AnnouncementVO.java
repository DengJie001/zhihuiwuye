package club.codemata.vo;

import club.codemata.bo.AnnouncementBO;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName AnnouncementVO
 * @Description 公告信息的表现层对象 用于前端信息展示
 * @createTime 2021/03/24 23:28:00
 */
public class AnnouncementVO {
    private List<AnnouncementBO> announcements;
    private int count;
    private int code;
    private String msg;

    public AnnouncementVO() {
    }

    public List<AnnouncementBO> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<AnnouncementBO> announcements) {
        this.announcements = announcements;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AnnouncementVO{" +
                "announcements=" + announcements +
                ", count=" + count +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
