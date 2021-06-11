package club.codemata.bo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceApplicationBO
 * @Description 场地申请记录业务层对象
 * @createTime 2021/04/10 23:48:00
 */
public class PlaceApplicationBO {
    private String applicationId;
    private int placeId;
    private String userTel;
    private String userName;
    private String applicationReason;
    private String resultId;
    private String result;
    private String resultDescription;
    private String managerName;
    private String managerTel;
    private String resultDate;
    private String applicationDate;
    private String beginDate;
    private String endDate;
    private int cost;
    private String payStatus;

    public PlaceApplicationBO() {
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

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
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

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
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

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public String toString() {
        return "PlaceApplicationBO{" +
                "applicationId='" + applicationId + '\'' +
                ", placeId=" + placeId +
                ", userTel='" + userTel + '\'' +
                ", userName='" + userName + '\'' +
                ", applicationReason='" + applicationReason + '\'' +
                ", resultId='" + resultId + '\'' +
                ", result='" + result + '\'' +
                ", resultDescription='" + resultDescription + '\'' +
                ", managerName='" + managerName + '\'' +
                ", managerTel='" + managerTel + '\'' +
                ", resultDate='" + resultDate + '\'' +
                ", applicationDate='" + applicationDate + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", cost=" + cost +
                ", payStatus='" + payStatus + '\'' +
                '}';
    }
}
