package club.codemata.model;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName Picture
 * @Description 前端展示图片所需要的图片类
 * @createTime 2021/04/19 18:50:00
 */
public class Picture {
    private String alt; // 图片名称
    private String pid; // 图片ID
    private String src; // 图片源地址
    private String thumb;   // 缩略图地址

    public Picture() {
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getPic() {
        return pid;
    }

    public void setPic(String pic) {
        this.pid = pic;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "alt='" + alt + '\'' +
                ", pic='" + pid + '\'' +
                ", src='" + src + '\'' +
                ", thumb='" + thumb + '\'' +
                '}';
    }
}
