package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LostItemComplaint
 * @Description 失物招领举报实体类
 * @createTime 2021/05/07 17:08:00
 */
public class LostItemComplaint {
    private String complaintId;
    private String lostItemId;
    private String complaintCategory;

    public LostItemComplaint() {
    }

    public LostItemComplaint(String complaintId, String lostItemId, String complaintCategory) {
        this.complaintId = complaintId;
        this.lostItemId = lostItemId;
        this.complaintCategory = complaintCategory;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getLostItemId() {
        return lostItemId;
    }

    public void setLostItemId(String lostItemId) {
        this.lostItemId = lostItemId;
    }

    public String getComplaintCategory() {
        return complaintCategory;
    }

    public void setComplaintCategory(String complaintCategory) {
        this.complaintCategory = complaintCategory;
    }

    @Override
    public String toString() {
        return "LostItemComplaint{" +
                "complaintId='" + complaintId + '\'' +
                ", lostItemId='" + lostItemId + '\'' +
                ", complaintCategory='" + complaintCategory + '\'' +
                '}';
    }
}
