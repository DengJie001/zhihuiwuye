package club.codemata.service.impl;

import club.codemata.dao.IQrCodeDao;
import club.codemata.entity.QrCode;
import club.codemata.service.IQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName QrCodeServiceImpl
 * @Description TODO
 * @createTime 2021/04/21 23:53:00
 */
@Service(value = "QrCodeService")
@Transactional(rollbackFor = Exception.class)
public class QrCodeServiceImpl implements IQrCodeService {
    private IQrCodeDao qrCodeDao;

    @Autowired
    @Qualifier("IQrCodeDao")
    public void setQrCodeDao(IQrCodeDao qrCodeDao) {
        this.qrCodeDao = qrCodeDao;
    }

    /**
     * 新增一张支付二维码
     * @Date 2021/4/21 23:55
     * @param codeId 二维码ID
     * @param qrCode 二维码链接
     * @return int
     **/
    @Override
    public int addQrCode(String codeId, String qrCode) throws Exception {
        QrCode code = new QrCode();
        code.setCodeId(codeId);
        code.setQrCode(qrCode);
        return qrCodeDao.saveQrCode(code);
    }

    /**
     * 更新保存的二维码链接
     * @Date 2021/4/21 23:56
     * @param codeId 被更新的二维码ID
     * @param qrCode 新的二维码链接
     * @return int
     **/
    @Override
    public int updateQrCode(String codeId, String qrCode) throws Exception {
        QrCode code = new QrCode();
        code.setQrCode(qrCode);
        if (codeId.indexOf("/") != -1) {
            code.setCodeId(codeId.substring(0, codeId.indexOf("/")));
        } else {
            code.setCodeId(codeId);
        }
        return qrCodeDao.updateQrCode(code);
    }

    /**
     * 获取一张支付码
     * @Date 2021/4/21 23:56
     * @param codeId 要获取的支付码的ID
     * @return club.codemata.entity.QrCode
     **/
    @Override
    public QrCode getQrCode(String codeId) throws Exception {
        return qrCodeDao.getQrCode(codeId);
    }
}
