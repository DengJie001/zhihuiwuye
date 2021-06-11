package club.codemata.bo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LostItemComplaintBO
 * @Description 失物招领举报信息业务层实体类
 * @createTime 2021/05/07 17:18:00
 */
public class LostItemComplaintBO {
    private String complaintId;
    private String lostItemId;
    private String date;    // 失物招领信息发布的时间
    private String itemPicture;
    private String itemDescription;
    private String itemCategory;
    private String complaintCategory;

    public LostItemComplaintBO() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemPicture() {
        return itemPicture;
    }

    public void setItemPicture(String itemPicture) {
        this.itemPicture = itemPicture;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getComplaintCategory() {
        return complaintCategory;
    }

    public void setComplaintCategory(String complaintCategory) {
        this.complaintCategory = complaintCategory;
    }

    @Override
    public String toString() {
        return "LostItemComplaintBO{" +
                "complaintId='" + complaintId + '\'' +
                ", lostItemId='" + lostItemId + '\'' +
                ", date='" + date + '\'' +
                ", itemPicture='" + itemPicture + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", complaintCategory='" + complaintCategory + '\'' +
                '}';
    }
}
