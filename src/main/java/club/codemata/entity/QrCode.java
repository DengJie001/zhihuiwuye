package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName QrCode
 * @Description 支付码保存
 * @createTime 2021/04/21 23:36:00
 */
public class QrCode {
    private String codeId;
    private String qrCode;
    private String date;

    public QrCode() {
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "QrCode{" +
                "codeId='" + codeId + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
