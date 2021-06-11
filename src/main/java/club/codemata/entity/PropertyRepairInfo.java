package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PropertyRepairInfo
 * @Description 物业维修信息实体类
 * @createTime 2021/04/21 11:56:00
 */
public class PropertyRepairInfo {
    private String id;
    private String userId;
    private String workerId;
    private String repairDescription;
    private String orderDate;
    private int cost;
    private String orderStatus;

    public PropertyRepairInfo() {
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

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getRepairDescription() {
        return repairDescription;
    }

    public void setRepairDescription(String repairDescription) {
        this.repairDescription = repairDescription;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "PropertyRepairInfo{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", workerId='" + workerId + '\'' +
                ", repairDescription='" + repairDescription + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", cost=" + cost +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
