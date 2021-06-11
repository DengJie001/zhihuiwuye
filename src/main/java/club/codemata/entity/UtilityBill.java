package club.codemata.entity;

import java.util.Calendar;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName UtilityBill
 * @Description 物业账单实体类
 * @createTime 2021/04/26 01:00:00
 */
public class UtilityBill {
    private String billId;
    private float billFigure;
    private String userId;
    private String areaId;
    private String unitId;
    private String roomId;
    private String billStatus;
    private String billDate;
    private String billCategory;
    private String managerId;

    public UtilityBill() {
    }

    public static String createBillId() {
        String billId = "";
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) > 8 ? calendar.get(Calendar.MONTH) : calendar.get(Calendar.MONTH) + 1);
        String date = String.valueOf(calendar.get(Calendar.DATE));
        billId = year + month + date + String.valueOf(calendar.getTimeInMillis());
        return billId;
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

    @Override
    public String toString() {
        return "UtilityBill{" +
                "billId='" + billId + '\'' +
                ", billFigure=" + billFigure +
                ", userId='" + userId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", billStatus='" + billStatus + '\'' +
                ", billDate='" + billDate + '\'' +
                ", billCategory='" + billCategory + '\'' +
                ", managerId='" + managerId + '\'' +
                '}';
    }
}
