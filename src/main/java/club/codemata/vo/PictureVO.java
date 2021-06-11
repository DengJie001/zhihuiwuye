package club.codemata.vo;

import club.codemata.model.Picture;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PictureVO
 * @Description 相册 前端显示
 * @createTime 2021/04/19 18:48:00
 */
public class PictureVO {
    private String title;   // 相册标题
    private String id;  // 相册ID
    private int start;   // 第一张显示的图片序号 默认0
    private List<Picture> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<Picture> getData() {
        return data;
    }

    public void setData(List<Picture> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PictureVO{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", start=" + start +
                ", data=" + data +
                '}';
    }
}
