package club.codemata.controller;

import club.codemata.entity.QrCode;
import club.codemata.service.IQrCodeService;
import club.codemata.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName QrCodeController
 * @Description 支付二维码控制器
 * @createTime 2021/04/21 23:58:00
 */
@Controller
@RequestMapping(value = "/QrCode/")
public class QrCodeController {
    private IQrCodeService qrCodeService;

    @Autowired
    @Qualifier(value = "QrCodeService")
    public void setQrCodeService(IQrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @RequestMapping(value = "addQrCode.do")
    @ResponseBody
    public MessageVO doAddQrCode(@RequestParam(value = "codeId") String codeId,
                                 @RequestParam(value = "qrCode") String qrCode) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = qrCodeService.addQrCode(codeId, qrCode);
            messageVO.setMsgId(res);
            if (res > 0) {
                messageVO.setMsgContent("二维码保存成功");
            } else {
                messageVO.setMsgContent("二维码保存失败");
            }
        } catch (Exception e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("发生异常");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "modifyCode.do")
    @ResponseBody
    public MessageVO doModifyCode(@RequestParam(value = "codeId") String codeId,
                                  @RequestParam(value = "qrCode") String qrCode) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = qrCodeService.updateQrCode(codeId, qrCode);
            messageVO.setMsgId(res);
            if (res > 0) {
                messageVO.setMsgContent("更新成功");
            } else {
                messageVO.setMsgContent("更新失败");
            }
        } catch (Exception e) {
            messageVO.setMsgContent("发生异常");
            messageVO.setMsgId(-1);
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "getQrCode.do")
    @ResponseBody
    public HashMap<String, Object> doGetQrCode(@RequestParam(value = "codeId") String codeId) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            QrCode code = qrCodeService.getQrCode(codeId);
            if (code == null) {
                res.put("status", "error");
            } else {
                res.put("status", "success");
                res.put("code", code);
            }
        } catch (Exception e) {
            res.put("status", "exception");
            e.printStackTrace();
        } finally {
            return res;
        }
    }
}
