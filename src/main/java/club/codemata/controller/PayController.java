package club.codemata.controller;

import club.codemata.entity.Payment;
import club.codemata.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PayController
 * @Description 支付
 * @createTime 2021/04/14 20:38:00
 */
@Controller
@RequestMapping("/pay/")
public class PayController {
    private IPaymentService paymentService;

    @Autowired
    @Qualifier("PaymentService")
    public void setPaymentService(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "addRepairPayment.do")
    @ResponseBody
    public String doAddRepairPayment(@RequestParam(value = "aoid", required = false) String aoid,
                          @RequestParam(value = "order_id", required = false) String order_id,
                          @RequestParam(value = "pay_price", required = false) String pay_price,
                          @RequestParam(value = "pay_time", required = false) String pay_time,
                          @RequestParam(value = "more", required = false) String more,
                          @RequestParam(value = "detail", required = false) String detail,
                          @RequestParam(value = "sign", required = false) String sign) {
        String status = null;
        try {
            int res = paymentService.addRepairPayment(aoid, order_id, pay_price, pay_time, more);
            if (res > 0) {
                status = "success";
            } else {
                status = "error";
            }
        } catch (Exception e) {
            status = "exception";
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    @RequestMapping(value = "addPlaceApplicationPayment.do")
    @ResponseBody
    public String doAddPlaceApplicationPayment(@RequestParam(value = "aoid", required = false) String aoid,
                                               @RequestParam(value = "order_id", required = false) String order_id,
                                               @RequestParam(value = "pay_price", required = false) String pay_price,
                                               @RequestParam(value = "pay_time", required = false) String pay_time,
                                               @RequestParam(value = "more", required = false) String more,
                                               @RequestParam(value = "detail", required = false) String detail,
                                               @RequestParam(value = "sign", required = false) String sign) {
        String status = null;
        try {
            int res = paymentService.addPlaceApplicationPayment(aoid, order_id, pay_price, pay_time, more);
            if (res > 0) {
                status = "success";
            } else {
                status = "error";
            }
        } catch (Exception e) {
            status = "exception";
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    @RequestMapping(value = "addUtilityBillPayment.do")
    @ResponseBody
    public String doAddUtilityBillPayment(@RequestParam(value = "aoid", required = false) String aoid,
                                               @RequestParam(value = "order_id", required = false) String order_id,
                                               @RequestParam(value = "pay_price", required = false) String pay_price,
                                               @RequestParam(value = "pay_time", required = false) String pay_time,
                                               @RequestParam(value = "more", required = false) String more,
                                               @RequestParam(value = "detail", required = false) String detail,
                                               @RequestParam(value = "sign", required = false) String sign) {
        String status = null;
        try {
            int res = paymentService.addUtilityBillPayment(aoid, order_id, pay_price, pay_time, more);
            if (res > 0) {
                status = "success";
            } else {
                status = "error";
            }
        } catch (Exception e) {
            status = "exception";
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    @RequestMapping(value = "getPayment.do")
    @ResponseBody
    public HashMap<String, Object> doGetPayment(@RequestParam(value = "billId") String billId) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Payment payment = paymentService.getPaymentByBillId(billId);
            if (payment != null) {
                res.put("status", "success");
                res.put("payment", payment);
            } else {
                res.put("status", "none");
            }
        } catch (Exception e) {
            res.put("status", "exception");
            e.printStackTrace();
        } finally {
            return res;
        }
    }
}
