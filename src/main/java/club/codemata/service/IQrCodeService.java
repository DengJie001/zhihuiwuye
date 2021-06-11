package club.codemata.service;

import club.codemata.entity.QrCode;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IQrCodeService
 * @Description 支付码业务层接口
 * @createTime 2021/04/21 23:50:00
 */
public interface IQrCodeService {
    public int addQrCode(String codeId, String qrCode) throws Exception;

    public int updateQrCode(String codeId, String qrCode) throws Exception;

    public QrCode getQrCode(String codeId) throws Exception;
}
