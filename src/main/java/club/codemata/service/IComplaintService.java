package club.codemata.service;

import club.codemata.bo.ComplaintBO;
import club.codemata.entity.Complaint;
import club.codemata.model.Picture;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IComplaintService
 * @Description 用户投诉建议业务层接口
 * @createTime 2021/04/17 15:19:00
 */
public interface IComplaintService {
    public int addComplaint(String userId, String category, String pictures, String content) throws Exception;

    public int updateComplaintResult(String complaintId, String result) throws Exception;

    public int updateComplaintManagerResponse(String complaintId, String managerId, String managerResponse) throws Exception;

    public int deleteComplaint(String complaintId) throws Exception;

    public List<ComplaintBO> getComplaints(String property, String value) throws Exception;

    public int count(String property,String value) throws Exception;

    public List<Picture> getPicturesByComplaintId(String complaintId) throws Exception;

    public ComplaintBO complaint2ComplaintBO(Complaint complaint) throws Exception;

    public int getUserUnreadComplaintReply(String userId) throws Exception;
}
