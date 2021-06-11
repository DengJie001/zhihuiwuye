package club.codemata.dao;

import club.codemata.entity.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IPaymentDao
 * @Description 订单支付数据库操作接口
 * @createTime 2021/04/21 11:30:00
 */
@Repository
public interface IPaymentDao {
    /**
     * 新增加一条支付订单消息
     * @Date 2021/4/21 11:31
     * @param payment
     * @return int
     **/
    public int savePayment(Payment payment) throws Exception;

    /**
     * 删除一条支付记录
     * @Date 2021/4/22 21:20
     * @param billId 账单ID
     * @return int
     **/
    public int removePayment(@Param("billId") String billId) throws Exception;

    /**
     * 修改一条支付记录
     * @Date 2021/4/22 21:26
     * @param payment 修改后的支付记录
     * @return int
     **/
    public int updatePayment(Payment payment) throws Exception;

    /**
     * 根据账单ID查找一条支付记录
     * @Date 2021/4/22 15:47
     * @param billId 账单ID
     * @return club.codemata.entity.Payment
     **/
    public Payment getPaymentByBillId(@Param("billId") String billId) throws Exception;

    /**
     * 根据账单ID查找一条支付记录<br>
     * 账单ID指的是物业维修ID，场地申请ID，物业缴费记录ID，而不是支付ID
     * @Date 2021/4/22 13:19
     * @param billId
     * @return int
     **/
    public int countPaymentByBillId(@Param("billId") String billId) throws Exception;
}
