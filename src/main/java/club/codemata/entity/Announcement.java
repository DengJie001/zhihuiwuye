package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName Announcement
 * @Description 公告信息实体类
 * @createTime 2021/03/23 14:47:00
 */
public class Announcement {
    private String id;
    private String title;
    private String author;
    private String content;
    private String date;
    private String top;

    public Announcement() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return "Announcement{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", top='" + top + '\'' +
                '}';
    }
}
