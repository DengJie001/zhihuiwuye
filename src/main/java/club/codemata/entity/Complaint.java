package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName Complaint
 * @Description 用户投诉建议实体类
 * @createTime 2021/04/17 00:40:00
 */
public class Complaint {
    private String id;
    private String userId;
    private String date;
    private String messageContent;  // 投诉或者建议的详细内容
    private String picture;
    private String managerId;
    private String managerResponse;
    private String responseDate;
    private String result;
    private String status;
    private String category;

    public Complaint() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerResponse() {
        return managerResponse;
    }

    public void setManagerResponse(String managerResponse) {
        this.managerResponse = managerResponse;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
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

    @Override
    public String toString() {
        return "Complaint{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", picture='" + picture + '\'' +
                ", managerId='" + managerId + '\'' +
                ", managerResponse='" + managerResponse + '\'' +
                ", responseDate='" + responseDate + '\'' +
                ", result='" + result + '\'' +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
