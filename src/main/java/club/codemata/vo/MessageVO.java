package club.codemata.vo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName MessageVO
 * @Description 返回前端的提示信息类
 * @createTime 2021/03/08 14:48:00
 */
public class MessageVO {
    private int msgId;
    private String msgContent;

    public MessageVO() {
    }

    public MessageVO(int msgId, String msgContent) {
        this.msgId = msgId;
        this.msgContent = msgContent;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
