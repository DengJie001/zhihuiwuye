package club.codemata.bo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName AnnouncementBO
 * @Description 公告信息业务层对象
 * @createTime 2021/03/24 00:35:00
 */
public class AnnouncementBO {
    private String id;
    private String authorName;
    private String authorId;
    private String authorTel;
    private String content;
    private String date;
    private String top;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AnnouncementBO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorTel() {
        return authorTel;
    }

    public void setAuthorTel(String authorTel) {
        this.authorTel = authorTel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "AnnouncementBO{" +
                "id='" + id + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorId='" + authorId + '\'' +
                ", authorTel='" + authorTel + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", top='" + top + '\'' +
                '}';
    }
}
