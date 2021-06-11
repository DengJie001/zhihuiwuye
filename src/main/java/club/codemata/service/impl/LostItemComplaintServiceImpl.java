package club.codemata.service.impl;

import club.codemata.bo.LostItemComplaintBO;
import club.codemata.dao.ILostItemComplaintDao;
import club.codemata.dao.ILostItemDao;
import club.codemata.entity.LostItem;
import club.codemata.entity.LostItemComplaint;
import club.codemata.model.Picture;
import club.codemata.service.ILostItemComplaintService;
import club.codemata.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LostItemComplaintServiceImpl
 * @Description TODO
 * @createTime 2021/05/07 17:16:00
 */
@Service(value = "LostItemComplaintService")
public class LostItemComplaintServiceImpl implements ILostItemComplaintService {
    private ILostItemComplaintDao complaintDao;
    private ILostItemDao itemDao;

    @Autowired
    @Qualifier(value = "ILostItemComplaintDao")
    public void setComplaintDao(ILostItemComplaintDao complaintDao) {
        this.complaintDao = complaintDao;
    }

    @Autowired
    @Qualifier(value = "ILostItemDao")
    public void setItemDao(ILostItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public int addComplaint(String lostItemId, String complaintCategory) throws Exception {
        LostItemComplaint complaint = new LostItemComplaint();
        complaint.setComplaintId(UUIDUtils.getUUID());
        complaint.setComplaintCategory(complaintCategory);
        complaint.setLostItemId(lostItemId);
        return complaintDao.saveComplaint(complaint);
    }

    @Override
    public List<LostItemComplaintBO> getAllLostItemComplaints() throws Exception {
        List<LostItemComplaintBO> bos = new ArrayList<>();
        List<LostItemComplaint> complaints = complaintDao.getAllLostItemComplaints();
        if (complaints.size() > 0) {
            for (LostItemComplaint complaint : complaints) {
                bos.add(lostItemComplaint2BO(complaint));
            }
        }
        return bos;
    }

    @Override
    public LostItemComplaintBO lostItemComplaint2BO(LostItemComplaint complaint) throws Exception {
        LostItemComplaintBO bo = new LostItemComplaintBO();
        bo.setComplaintId(complaint.getComplaintId());
        bo.setComplaintCategory(complaint.getComplaintCategory());
        bo.setItemCategory(itemDao.getLostItemById(complaint.getLostItemId()).getItemCategory());
        bo.setItemPicture(itemDao.getLostItemById(complaint.getLostItemId()).getItemPicture());
        bo.setItemDescription(itemDao.getLostItemById(complaint.getLostItemId()).getItemDescription());
        bo.setDate(itemDao.getLostItemById(complaint.getLostItemId()).getItemDate());
        bo.setLostItemId(complaint.getLostItemId());
        return bo;
    }

    @Override
    public List<Picture> getPictures(String lostItemId) throws Exception {
        LostItem item = itemDao.getLostItemById(lostItemId);
        List<Picture> urls = new ArrayList<>();
        String pictureStr = item.getItemPicture();
        String[] pictures = pictureStr.split("\\+");
        for (int i = 0; i < pictures.length; ++i) {
            Picture picture = new Picture();
            picture.setThumb(pictures[i]);
            picture.setSrc(pictures[i]);
            picture.setAlt("失物招领图片" + i);
            picture.setPic(UUIDUtils.getUUID());
            urls.add(picture);
        }
        return urls;
    }

    @Override
    public int count() throws Exception {
        return complaintDao.count();
    }
}
