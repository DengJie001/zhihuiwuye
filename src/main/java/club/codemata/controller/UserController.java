package club.codemata.controller;

import club.codemata.utils.SMSUtils;
import club.codemata.vo.MessageVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName UserController
 * @Description TODO
 * @createTime 2021/03/18 13:28:00
 */
@Controller
@RequestMapping(value = "/user/")
public class UserController {
    @RequestMapping(value = "getUserOpenid.do")
    @ResponseBody
    public String doGetUserOpenid(@RequestParam("code") String code) throws Exception {
        System.out.println("code:" + code);
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=wx9c1f95f2cd5fc0db";
        url += "&secret=cc401286157aa8075bbd5bf39f31bef3";
        url += "&js_code=" + code;
        url += "&grant_type=authorization_code";
        url += "&connect_redirect=1";
        String res = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;

        // 配置信息

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)    // 设置连接超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)  // 设置请求超时时间
                .setSocketTimeout(5000) // socket读写超时时间
                .setRedirectsEnabled(false).build();    // 设置是否允许重定向
        httpGet.setConfig(requestConfig);   // 将以上配置信息再这个Get中使用
        response = httpClient.execute(httpGet);

        HttpEntity responseEntity = response.getEntity();
        System.out.println("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            res = EntityUtils.toString(responseEntity);
            System.out.println("相应内容长度:" + responseEntity.getContentLength());
            System.out.println("相应内容为:" + res);
        }

        // 释放资源
        if (httpClient != null) {
            httpClient.close();
        }
        if (response != null) {
            response.close();
        }
        return res;
    }

    @RequestMapping(value = "register.do")
    @ResponseBody
    public MessageVO doRegister(HttpServletRequest request, @RequestParam("verifyCode") String code) {
        System.out.println(code);
        MessageVO messageVO = new MessageVO();
        JSONObject json = (JSONObject) request.getSession().getAttribute("code");
        System.out.println("控制器json:" + json);
        System.out.println("发送时间:" + json.getString("sendTime"));
        if (System.currentTimeMillis() - Long.parseLong(json.getString("sendTime")) > Integer.parseInt(SMSUtils.TIMEOUT) * 60 * 1000) {
            messageVO.setMsgId(0);
            messageVO.setMsgContent("验证码已过期");
            return messageVO;
        }
        if (!json.getString("code").equals(code)) {
            messageVO.setMsgId(0);
            messageVO.setMsgContent("验证码错误");
        } else {
            messageVO.setMsgId(1);
            messageVO.setMsgContent("注册成功");
        }
        // TODO 将数据存入数据库
        return messageVO;
    }
}
