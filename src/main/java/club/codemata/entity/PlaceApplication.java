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
    private String applicationUserId;
    private String applicationManager;
    private String applicationReason;
    private String applicationResult;
    private String applicationStatus;
    private String applicationDate;
    private String beginDate;
    private String endDate;
    private String resultDescription;
    private int cost;

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

    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public String getApplicationManager() {
        return applicationManager;
    }

    public void setApplicationManager(String applicationManager) {
        this.applicationManager = applicationManager;
    }

    public String getApplicationReason() {
        return applicationReason;
    }

    public void setApplicationReason(String applicationReason) {
        this.applicationReason = applicationReason;
    }

    public String getApplicationResult() {
        return applicationResult;
    }

    public void setApplicationResult(String applicationResult) {
        this.applicationResult = applicationResult;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
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

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "PlaceApplication{" +
                "applicationId='" + applicationId + '\'' +
                ", placeId=" + placeId +
                ", applicationUserId='" + applicationUserId + '\'' +
                ", applicationManager='" + applicationManager + '\'' +
                ", applicationReason='" + applicationReason + '\'' +
                ", applicationResult='" + applicationResult + '\'' +
                ", applicationStatus='" + applicationStatus + '\'' +
                ", applicationDate='" + applicationDate + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", resultDescription='" + resultDescription + '\'' +
                ", cost=" + cost +
                '}';
    }
}
