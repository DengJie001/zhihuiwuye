package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceApplication
 * @Description 活动场地申请实体类
 * @createTime 2021/03/27 18:01:00
 */
public class PlaceApplication {
    private String applicationId;
    private int placeId;
    private String applicationUser;
    private String applicationReason;
    private String resultId;
    private String applicationDate;
    private String beginDate;
    private String endDate;
    private int cost;
    private String applicationNote;

    public PlaceApplication() {
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(String applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getApplicationReason() {
        return applicationReason;
    }

    public void setApplicationReason(String applicationReason) {
        this.applicationReason = applicationReason;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getApplicationNote() {
        return applicationNote;
    }

    public void setApplicationNote(String applicationNote) {
        this.applicationNote = applicationNote;
    }

    @Override
    public String toString() {
        return "PlaceApplication{" +
                "applicationId='" + applicationId + '\'' +
                ", placeId=" + placeId +
                ", applicationUser='" + applicationUser + '\'' +
                ", applicationReason='" + applicationReason + '\'' +
                ", resultId='" + resultId + '\'' +
                ", applicationDate='" + applicationDate + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", cost=" + cost +
                ", applicationNote='" + applicationNote + '\'' +
                '}';
    }
}
