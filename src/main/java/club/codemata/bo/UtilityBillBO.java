package club.codemata.bo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName UtilityBillBO
 * @Description 物业账单业务层实体对象
 * @createTime 2021/04/26 02:59:00
 */
public class UtilityBillBO {
    private String billId;
    private float billFigure;
    private String userId;
    private String userName;
    private String areaId;
    private String unitId;
    private String roomId;
    private String billStatus;
    private String billDate;
    private String billCategory;
    private String managerId;
    private String managerName;
    private String managerTel;

    public UtilityBillBO() {
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public float getBillFigure() {
        return billFigure;
    }

    public void setBillFigure(float billFigure) {
        this.billFigure = billFigure;
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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillCategory() {
        return billCategory;
    }

    public void setBillCategory(String billCategory) {
        this.billCategory = billCategory;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
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

    @Override
    public String toString() {
        return "UtilityBillBO{" +
                "billId='" + billId + '\'' +
                ", billFigure=" + billFigure +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", areaId='" + areaId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", billStatus='" + billStatus + '\'' +
                ", billDate='" + billDate + '\'' +
                ", billCategory='" + billCategory + '\'' +
                ", managerId='" + managerId + '\'' +
                ", managerName='" + managerName + '\'' +
                ", managerTel='" + managerTel + '\'' +
                '}';
    }
}
