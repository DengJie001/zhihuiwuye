package club.codemata.bo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ComplaintBO
 * @Description 用户投诉建议的业务层实体对象
 * @createTime 2021/04/17 15:04:00
 */
public class ComplaintBO {
    private String id;
    private String userId;
    private String complaintDate;
    private String messageContent;
    private String complaintPicture;
    private String managerId;
    private String managerName;
    private String managerTel;
    private String managerResponse;
    private String responseDate;
    private String result;
    private String status;
    private String category;

    public ComplaintBO() {
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

    public String getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getComplaintPicture() {
        return complaintPicture;
    }

    public void setComplaintPicture(String complaintPicture) {
        this.complaintPicture = complaintPicture;
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

    public String getManagerResponse() {
        return managerResponse;
    }

    public void setManagerResponse(String managerResponse) {
        this.managerResponse = managerResponse;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    @Override
    public String toString() {
        return "ComplaintBO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", complaintDate='" + complaintDate + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", complaintPicture='" + complaintPicture + '\'' +
                ", managerId='" + managerId + '\'' +
                ", managerName='" + managerName + '\'' +
                ", managerTel='" + managerTel + '\'' +
                ", managerResponse='" + managerResponse + '\'' +
                ", responseDate='" + responseDate + '\'' +
                ", result='" + result + '\'' +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
