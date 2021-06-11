package club.codemata.bo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LostItemBO
 * @Description 失物招领业务层实体对象
 * @createTime 2021/04/28 23:32:00
 */
public class LostItemBO {
    private String id;
    private String userId;
    private String userName;
    private String userTel;
    private String itemDescription;
    private String itemPicture;
    private String itemCategory;
    private String itemStatus;
    private String itemDate;

    public LostItemBO() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
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
        return "LostItemBO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userTel='" + userTel + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemPicture='" + itemPicture + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemStatus='" + itemStatus + '\'' +
                ", itemDate='" + itemDate + '\'' +
                '}';
    }
}
