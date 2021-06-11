package club.codemata.service;

import club.codemata.bo.LostItemComplaintBO;
import club.codemata.entity.LostItemComplaint;
import club.codemata.model.Picture;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ILostItemComplaintService
 * @Description 失物招领信息举报业务层接口
 * @createTime 2021/05/07 17:14:00
 */
public interface ILostItemComplaintService {
    public int addComplaint(String lostItemId, String complaintCategory) throws Exception;

    public List<LostItemComplaintBO> getAllLostItemComplaints() throws Exception;

    public LostItemComplaintBO lostItemComplaint2BO(LostItemComplaint complaint) throws Exception;

    public List<Picture> getPictures(String lostItemId) throws Exception;

    public int count() throws Exception;
}
