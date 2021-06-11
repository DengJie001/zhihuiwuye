package club.codemata.service;

import club.codemata.entity.Payment;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IPaymentService
 * @Description 订单支付业务层接口
 * @createTime 2021/04/21 11:36:00
 */
public interface IPaymentService {
    public int addRepairPayment(String aoid, String order_id, String pay_price, String pay_time, String more) throws Exception;

    public int addPlaceApplicationPayment(String aoid, String order_id, String pay_price, String pay_time, String more) throws Exception;

    public int addUtilityBillPayment(String aoid, String order_id, String pay_price, String pay_time, String more) throws Exception;

    public Payment getPaymentByBillId(String billId) throws Exception;
}
