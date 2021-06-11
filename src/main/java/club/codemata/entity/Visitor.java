package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName Visitor
 * @Description 来访人员信息实体类
 * @createTime 2021/05/08 17:09:00
 */
public class Visitor {
    private int visitorId;
    private String visitorName;
    private String visitorTel;
    private float visitorTemperature;
    private String visitorHomeAddress;
    private String visitorWorkAddress;
    private String visitDate;

    public Visitor() {
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorTel() {
        return visitorTel;
    }

    public void setVisitorTel(String visitorTel) {
        this.visitorTel = visitorTel;
    }

    public float getVisitorTemperature() {
        return visitorTemperature;
    }

    public void setVisitorTemperature(float visitorTemperature) {
        this.visitorTemperature = visitorTemperature;
    }

    public String getVisitorHomeAddress() {
        return visitorHomeAddress;
    }

    public void setVisitorHomeAddress(String visitorHomeAddress) {
        this.visitorHomeAddress = visitorHomeAddress;
    }

    public String getVisitorWorkAddress() {
        return visitorWorkAddress;
    }

    public void setVisitorWorkAddress(String visitorWorkAddress) {
        this.visitorWorkAddress = visitorWorkAddress;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "visitorId=" + visitorId +
                ", visitorName='" + visitorName + '\'' +
                ", visitorTel='" + visitorTel + '\'' +
                ", visitorTemperature='" + visitorTemperature + '\'' +
                ", visitorHomeAddress='" + visitorHomeAddress + '\'' +
                ", visitorWorkAddress='" + visitorWorkAddress + '\'' +
                ", visitDate='" + visitDate + '\'' +
                '}';
    }
}
