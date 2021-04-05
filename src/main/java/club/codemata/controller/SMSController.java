package club.codemata.controller;

import club.codemata.utils.SMSUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName SMSController
 * @Description 验证码发送控制器
 * @createTime 2021/03/18 18:38:00
 */
@Controller
@RequestMapping(value = "/SMS/")
public class SMSController {
    @RequestMapping(value = "sendCode.do")
    @ResponseBody
    public JSONObject doSendCode(HttpServletRequest request, @RequestParam("phoneNumber") String phoneNumber) {
        JSONObject res = SMSUtils.sendSMS(request, phoneNumber);
        return res;
    }
}