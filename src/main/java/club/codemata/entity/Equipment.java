package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName Equipment
 * @Description 设备资产实体类
 * @createTime 2021/03/09 18:42:00
 */
public class Equipment {
    private String equipmentId;
    private String equipmentName;
    private float equipmentPrice;
    private String equipmentPicture;
    private String equipmentBrand;
    private String equipmentType;
    private int equipmentQuantity;
    private String managerId;
    private String date;
    private String buyDate;

    public Equipment() {
    }

    public Equipment(String equipmentId, String equipmentName, float equipmentPrice, String equipmentPicture, String equipmentBrand, String equipmentType, int equipmentQuantity, String managerId, String date, String buyDate) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentPrice = equipmentPrice;
        this.equipmentPicture = equipmentPicture;
        this.equipmentBrand = equipmentBrand;
        this.equipmentType = equipmentType;
        this.equipmentQuantity = equipmentQuantity;
        this.managerId = managerId;
        this.date = date;
        this.buyDate = buyDate;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public float getEquipmentPrice() {
        return equipmentPrice;
    }

    public void setEquipmentPrice(float equipmentPrice) {
        this.equipmentPrice = equipmentPrice;
    }

    public String getEquipmentPicture() {
        return equipmentPicture;
    }

    public void setEquipmentPicture(String equipmentPicture) {
        this.equipmentPicture = equipmentPicture;
    }

    public String getEquipmentBrand() {
        return equipmentBrand;
    }

    public void setEquipmentBrand(String equipmentBrand) {
        this.equipmentBrand = equipmentBrand;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public int getEquipmentQuantity() {
        return equipmentQuantity;
    }

    public void setEquipmentQuantity(int equipmentQuantity) {
        this.equipmentQuantity = equipmentQuantity;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentId='" + equipmentId + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentPrice=" + equipmentPrice +
                ", equipmentPicture='" + equipmentPicture + '\'' +
                ", equipmentBrand='" + equipmentBrand + '\'' +
                ", equipmentType='" + equipmentType + '\'' +
                ", equipmentQuantity=" + equipmentQuantity +
                ", managerId='" + managerId + '\'' +
                ", date='" + date + '\'' +
                ", buyDate='" + buyDate + '\'' +
                '}';
    }
}
