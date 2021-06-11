package club.codemata.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName SMSUtils
 * @Description 短信验证码工具类
 * @createTime 2021/03/18 16:52:00
 */
public class SMSUtils {
    public static String TIMEOUT = "5";
    public static JSONObject sendSMS(HttpServletRequest request, String phoneNumber) {
        // 短信应用SDK AppID
        int appId = 1400496310;
        // 短信应用SDK appKey
        String appKey = "a1061546f3366d5c38326aab6f44dce3";
        // 短信应用模板ID
        int templateId = 896700;
        // 短信应用
        String smsSign = "乐享智慧物业";
        // 随机生成四位验证码
        String code = CodeUtils.get4Code();
        JSONObject json = new JSONObject(); // 存入session
        JSONObject res = new JSONObject();  // 返回前端与存入session的json相比 少了一个code
        HttpSession session = request.getSession();
        try {
            // 对应短信模板中的参数顺序和个数
            String[] params = {code, SMSUtils.TIMEOUT};
            // 创建sender对象
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            // 发送
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");
            if (result.result != 0) {
                res.put("sendTime", System.currentTimeMillis());
                res.put("sessionId", null);
                session.setAttribute("code", json.toString());
                return res;
            }
            // JSONObject存入数据并且存入session
            json.put("code", code); // 存入验证码
            json.put("sendTime", System.currentTimeMillis());   // 存入验证码发送的时间
            json.put("sessionId", session.getId());
            // 组装返回前端的json
            res.put("sendTime", System.currentTimeMillis());
            res.put("sessionId", session.getId());
            // 将验证码及其发送时间存入session
            session.setAttribute("code", json);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        } catch (Exception e) {
            // 其他错误
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 给用户发送短信 提醒投诉建议已经成功提交到数据库
     * @Date 2021/4/17 17:19
     * @param phoneNumber 用户手机号码
     * @param category 分类
     * @param lastName 用户姓氏
     * @return int
     **/
    public static void sendComplaintNoticeToUser(String phoneNumber, String category, String lastName) throws Exception {
        // 短信应用SDK AppID
        int appId = 1400496310;

        // 短信应用SDK appKey
        String appKey = "a1061546f3366d5c38326aab6f44dce3";

        // 短信模板ID
        int templateId = 928163;

        // 签名
        String smsSign = "乐享智慧物业";

        try {
            // 短信中的参数 于模板中需要顺序一一对应
            String[] params = { lastName, category };
            // 创建sender对象
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            // 发送
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendManagerReplyNoticeToUser(String phoneNumber, String category, String lastName, String complaintDate) throws Exception {
        // 短信应用appid
        int appId = 1400496310;

        // 短信应用SDK appKey
        String appKey = "a1061546f3366d5c38326aab6f44dce3";

        // 模板ID
        int templateId = 929894;

        // 签名
        String smsSign = "乐享智慧物业";
        try {
            // 短信参数 要与模板一一对应
            String[] params = { lastName, complaintDate, category };
            // 创建sender对象
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            // 发送短信
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendToWorkerCancelRepair(String repairIdSub1, String repairIdSub2, String repairIdSub3, String repairIdSub4, String repairIdSub5, String phoneNumber) throws Exception {
        int appId = 1400496310;
        String appKey = "a1061546f3366d5c38326aab6f44dce3";
        int templateId = 934103;
        String smsSign = "乐享智慧物业";
        try {
            String[] params = { repairIdSub1, repairIdSub2, repairIdSub3, repairIdSub4, repairIdSub5 };
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendToWorkerRepairInfo(String phoneNumber, String userLastName, String repairIdSub1, String repairIdSub2, String repairIdSub3, String repairIdSub4, String repairIdSub5, int repairInfoTotal) throws Exception {
        int appId = 1400496310;
        String appKey = "a1061546f3366d5c38326aab6f44dce3";
        int templateId = 934105;
        String smsSign = "乐享智慧物业";
        try {
            String[] params = { userLastName, repairIdSub1, repairIdSub2, repairIdSub3, repairIdSub4, repairIdSub5, String.valueOf(repairInfoTotal) };
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendUrgePayBill(String phoneNumber, String year, String month, String billCategory, String managerTel) {
        int appId = 1400496310;
        String appKey = "a1061546f3366d5c38326aab6f44dce3";
        int templatedId = 949865;
        String smsSign = "乐享智慧物业";
        try {
            String[] params = { year, month, billCategory, managerTel };
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber, templatedId, params, smsSign, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendUtilityBill(String phoneNumber, String month, String billCategory) {
        int appId = 1400496310;
        String appKey = "a1061546f3366d5c38326aab6f44dce3";
        int templatedId = 938601;
        String smsSign = "乐享智慧物业";
        try {
            String[] params = { month, billCategory };
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber, templatedId, params, smsSign, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendDeleteLostItemInfo(String phoneNumber, String year, String month, String date, String itemCategory, String complaintCategory) {
        int appId = 1400496310;
        String appKey = "a1061546f3366d5c38326aab6f44dce3";
        int templatedId = 950140;
        String smsSign = "乐享智慧物业";
        try {
            String[] params = { year, month, date, itemCategory, complaintCategory };
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber, templatedId, params, smsSign, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
