package club.codemata.dao;

import club.codemata.entity.QrCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IQrCodeDao
 * @Description 支付码数据库操作接口
 * @createTime 2021/04/21 23:37:00
 */
@Repository
public interface IQrCodeDao {
    /**
     * 保存一张支付码
     * @Date 2021/4/21 23:38
     * @param code 支付码对象 包括id和二维码链接
     * @return int
     **/
    public int saveQrCode(QrCode code) throws Exception;

    /**
     * 删除一条支付码信息
     * @Date 2021/4/22 21:18
     * @param codeId 要删除的支付码的ID
     * @return int
     **/
    public int removeQrCode(@Param("codeId") String codeId) throws Exception;

    /**
     * 更新支付码
     * @Date 2021/4/21 23:42
     * @param qrCode 更新后的支付码
     * @return int
     **/
    public int updateQrCode(QrCode qrCode) throws Exception;

    /**
     * 获取一张支付码
     * @Date 2021/4/21 23:38
     * @param codeId
     * @return int
     **/
    public QrCode getQrCode(@Param("codeId") String codeId) throws Exception;
}
