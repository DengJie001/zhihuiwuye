package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LostItem
 * @Description 失物招领信息实体类
 * @createTime 2021/04/28 22:42:00
 */
public class LostItem {
    private String id;
    private String userId;
    private String itemDescription;
    private String itemPicture;
    private String itemCategory;
    private String itemStatus;
    private String itemDate;

    public LostItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPicture() {
        return itemPicture;
    }

    public void setItemPicture(String itemPicture) {
        this.itemPicture = itemPicture;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    @Override
    public String toString() {
        return "LostItem{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemPicture='" + itemPicture + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemStatus='" + itemStatus + '\'' +
                ", itemDate='" + itemDate + '\'' +
                '}';
    }
}
