package club.codemata.service.impl;

import club.codemata.dao.IPaymentDao;
import club.codemata.dao.IUtilityBillDao;
import club.codemata.entity.Payment;
import club.codemata.service.IPaymentService;
import club.codemata.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PaymentServiceImpl
 * @Description TODO
 * @createTime 2021/04/21 11:39:00
 */
@Service(value = "PaymentService")
public class PaymentServiceImpl implements IPaymentService {
    private IPaymentDao paymentDao;
    private IUtilityBillDao billDao;

    @Autowired
    @Qualifier("IPaymentDao")
    public void setPaymentDao(IPaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Autowired
    @Qualifier(value = "IUtilityBillDao")
    public void setBillDao(IUtilityBillDao billDao) {
        this.billDao = billDao;
    }

    @Override
    public int addRepairPayment(String aoid, String order_id, String pay_price, String pay_time, String more) throws Exception {
        Payment payment = new Payment();
        payment.setPayId(aoid);
        // 由于可能存在是多次获取二维码后才进行支付 从第二次开始获取的二维码的order_id中会包含 /当前时间 的字符串 需要删掉这部分字符串
        // 如果结果不为-1 则证明这里面包含了上述的不需要的字符串 需要删除
        if (order_id.indexOf("/") != -1) {
            payment.setBillId(order_id.substring(0, order_id.indexOf("/")));
        } else {
            payment.setBillId(order_id);
        }
        // 将金额字符串使用\\.进行分隔 拿到整数值部分
        String[] figures = pay_price.split("\\.");
        payment.setFigure(new Integer(figures[0]));
        payment.setPayTime(DateUtils.getNowTime());
        payment.setBillName("物业维修支付");
        return paymentDao.savePayment(payment);
    }

    @Override
    public int addPlaceApplicationPayment(String aoid, String order_id, String pay_price, String pay_time, String more) throws Exception {
        Payment payment = new Payment();
        payment.setPayId(aoid);
        // 由于可能存在是多次获取二维码后才进行支付 从第二次开始获取的二维码的order_id中会包含 /当前时间 的字符串 需要删掉这部分字符串
        // 如果结果不为-1 则证明这里面包含了上述的不需要的字符串 需要删除
        if (order_id.indexOf("/") != -1) {
            payment.setBillId(order_id.substring(0, order_id.indexOf("/")));
        } else {
            payment.setBillId(order_id);
        }
        // 将金额字符串使用\\.进行分隔 拿到整数值部分
        String[] figures = pay_price.split("\\.");
        payment.setFigure(new Integer(figures[0]));
        payment.setPayTime(DateUtils.getNowTime());
        payment.setBillName("场地申请费用支付");
        return paymentDao.savePayment(payment);
    }

    @Override
    public int addUtilityBillPayment(String aoid, String order_id, String pay_price, String pay_time, String more) throws Exception {
        Payment payment = new Payment();
        payment.setPayId(aoid);
        // 由于可能存在是多次获取二维码后才进行支付 从第二次开始获取的二维码的order_id中会包含 /当前时间 的字符串 需要删掉这部分字符串
        // 如果结果不为-1 则证明这里面包含了上述的不需要的字符串 需要删除
        if (order_id.indexOf("/") != -1) {
            payment.setBillId(order_id.substring(0, order_id.indexOf("/")));
        } else {
            payment.setBillId(order_id);
        }
        // 将金额字符串使用\\.进行分隔 拿到整数值部分
        payment.setFigure(new Float(pay_price));
        payment.setPayTime(DateUtils.getNowTime());
        payment.setBillName("物业账单支付");
        billDao.updateUtilityBillStatus(payment.getBillId(), "已缴费");
        return paymentDao.savePayment(payment);
    }

    /**
     * 根据账单ID查找一条支付记录
     * @Date 2021/4/22 15:50
     * @param billId 账单ID
     * @return club.codemata.entity.Payment
     **/
    @Override
    public Payment getPaymentByBillId(String billId) throws Exception {
        return paymentDao.getPaymentByBillId(billId);
    }
}
