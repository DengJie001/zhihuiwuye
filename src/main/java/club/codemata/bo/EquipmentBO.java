package club.codemata.bo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName EquipmentBO
 * @Description 设备新的业务层对象 包括设备信息,设备对应的管理员姓名和电话
 * @createTime 2021/03/11 09:56:00
 */
public class EquipmentBO {
    private String equipmentId;
    private String equipmentName;
    private String equipmentPicture;
    private String equipmentBrand;
    private String equipmentType;
    private float equipmentPrice;
    private int equipmentQuantity;
    private String managerId;
    private String buyDate;
    private String managerName;
    private String managerTel;

    public EquipmentBO() {
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

    public float getEquipmentPrice() {
        return equipmentPrice;
    }

    public void setEquipmentPrice(float equipmentPrice) {
        this.equipmentPrice = equipmentPrice;
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

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerTel() {
        return managerTel;
    }

    public void setManagerTel(String managerTel) {
        this.managerTel = managerTel;
    }
}
