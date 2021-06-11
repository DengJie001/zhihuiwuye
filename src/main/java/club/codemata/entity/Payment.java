package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName Payment
 * @Description 订单支付实体类
 * @createTime 2021/04/21 11:27:00
 */
public class Payment {
    private String payId;
    private String billId;
    private float figure;
    private String payTime;
    private String billName;

    public Payment() {
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public float getFigure() {
        return figure;
    }

    public void setFigure(float figure) {
        this.figure = figure;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payId='" + payId + '\'' +
                ", billId='" + billId + '\'' +
                ", figure=" + figure +
                ", payTime='" + payTime + '\'' +
                ", billName='" + billName + '\'' +
                '}';
    }
}
