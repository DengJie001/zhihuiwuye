package club.codemata.service.impl;

import club.codemata.bo.ComplaintBO;
import club.codemata.dao.IComplaintDao;
import club.codemata.dao.IManagerDao;
import club.codemata.dao.IUserDao;
import club.codemata.entity.Complaint;
import club.codemata.entity.Manager;
import club.codemata.entity.User;
import club.codemata.model.Picture;
import club.codemata.service.IComplaintService;
import club.codemata.utils.DateUtils;
import club.codemata.utils.SMSUtils;
import club.codemata.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ComplaintServiceImpl
 * @Description 用户投诉建议业务层逻辑
 * @createTime 2021/04/17 15:34:00
 */
@Service(value = "ComplaintService")
@Transactional(rollbackFor = Exception.class)
public class ComplaintServiceImpl implements IComplaintService {
    private IComplaintDao complaintDao;
    private IManagerDao managerDao;
    private IUserDao userDao;

    @Autowired
    @Qualifier("IComplaintDao")
    public void setComplaintDao(IComplaintDao complaintDao) {
        this.complaintDao = complaintDao;
    }

    @Autowired
    @Qualifier("IManagerDao")
    public void setManagerDao(IManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @Autowired
    @Qualifier("IUserDao")
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 用户添加一条投诉/建议<br>
     * 在存入数据库成功之后 给用户和管理员发送通知消息
     * @Date 2021/4/17 15:41
     * @param userId
     * @param category
     * @param pictures
     * @param content
     * @return int
     **/
    @Override
    public int addComplaint(String userId, String category, String pictures, String content) throws Exception {
        Complaint complaint = new Complaint();
        complaint.setId(UUIDUtils.getUUID());
        complaint.setUserId(userId);
        complaint.setCategory(category);
        complaint.setMessageContent(content);
        complaint.setPicture(pictures.equals("null") ? "" : pictures.substring(1));
        complaint.setStatus("未处理");
        int res = complaintDao.saveComplaint(complaint);
        // res大于0 则证明新增成功 向用户和管理员发送通知消息
        // 根据userId查找用户电话号码和姓名
        User user = userDao.getUserById(userId);
        String userLastName = user.getUserName().substring(0, 1);
        if (res > 0) {
            SMSUtils.sendComplaintNoticeToUser(user.getUserTel(), category, userLastName);
        }
        return res;
    }

    /**
     * 修改用户查看管理员之后的意见
     * @Date 2021/4/19 22:22
     * @param complaintId 对应的投诉/建议的ID
     * @param result
     * @return int
     **/
    @Override
    public int updateComplaintResult(String complaintId, String result) throws Exception {
        return complaintDao.updateComplaintResult(complaintId, result);
    }

    /**
     * 更新管理员回复内容
     * @Date 2021/4/19 16:23
     * @param complaintId 投诉建议ID
     * @param managerId 管理员ID
     * @param managerResponse 管理员的回复内容
     * @return int
     **/
    @Override
    public synchronized int updateComplaintManagerResponse(String complaintId, String managerId, String managerResponse) throws Exception {
        // 先要查询管理员是否已经回复过
        Complaint complaint = complaintDao.getComplaintById(complaintId);
        // 如果查出来对应的管理员ID不为空 则证明已经回复过了
        if (complaint.getManagerId() != null) {
            return -1;
        }
        // 执行更新管理员回复内容
        int res = complaintDao.updateComplaintManagerResponse(complaintId, managerId, managerResponse, DateUtils.getNowDate());
        // 更新处理状态
        complaintDao.updateComplaintStatus(complaintId, "已处理");
        // 向用户发送信息 提示已回复
        User user = userDao.getUserById(complaint.getUserId());
        String lastName = user.getUserName().substring(0, 1);
        String complaintDate = complaint.getDate();
        String phoneNumber = user.getUserTel();
        String category = complaint.getCategory();
        SMSUtils.sendManagerReplyNoticeToUser(phoneNumber, category, lastName, complaintDate);
        return res;
    }

    /**
     * 删除一条投诉建议
     * @Date 2021/4/19 13:48
     * @param complaintId 要删除的投诉建议的ID
     * @return int 删除后的结果
     **/
    @Override
    public int deleteComplaint(String complaintId) throws Exception {
        int res = complaintDao.removeComplaint(complaintId);
        return res;
    }

    /**
     * 根据属性和值查找投诉建议信息<br>
     * 如果两个参数都是null 则查找所有的投诉建议记录
     * @Date 2021/4/17 18:01
     * @param property 属性
     * @param value 值
     * @return java.util.List<club.codemata.bo.ComplaintBO>
     **/
    @Override
    public List<ComplaintBO> getComplaints(String property, String value) throws Exception {
        List<ComplaintBO> complaintBOS = new ArrayList<>();
        if ("处理状态".equals(property)) {
            List<Complaint> complaints = complaintDao.getComplaintsByStatus(value);
            if (complaints.size() > 0) {
                for (Complaint complaint : complaints) {
                    complaintBOS.add(complaint2ComplaintBO(complaint));
                }
            }
        } else if ("用户ID".equals(property)) {
            List<Complaint> complaints = complaintDao.getComplaintsByUserId(value);
            if (complaints.size() > 0) {
                for (Complaint complaint : complaints) {
                    complaintBOS.add(complaint2ComplaintBO(complaint));
                }
            }
        } else if ("用户意见".equals(property)) {
            List<Complaint> complaints = complaintDao.getComplaintsByResult(value);
            if (complaints.size() > 0) {
                for (Complaint complaint : complaints) {
                    complaintBOS.add(complaint2ComplaintBO(complaint));
                }
            }
        } else if ("处理人".equals(property)) {
            List<Complaint> complaints = complaintDao.getComplaintsByManagerId(value);
            if (complaints.size() > 0) {
                for (Complaint complaint : complaints) {
                    complaintBOS.add(complaint2ComplaintBO(complaint));
                }
            }
        } else {
            List<Complaint> allComplaints = complaintDao.getAllComplaints();
            if (allComplaints.size() > 0) {
                for (Complaint complaint : allComplaints) {
                    complaintBOS.add(complaint2ComplaintBO(complaint));
                }
            }
        }
        return complaintBOS;
    }

    @Override
    public int count(String property, String value) throws Exception {
        return complaintDao.count(property, value);
    }

    @Override
    public List<Picture> getPicturesByComplaintId(String complaintId) throws Exception {
        List<Picture> pictures = new ArrayList<>();
        Complaint complaint = complaintDao.getComplaintById(complaintId);
        if (complaint.getPicture() != null) {
            String[] urls = complaint.getPicture().split("\\+");
            for (int i = 0; i < urls.length; ++i) {
                Picture picture = new Picture();
                picture.setPic(UUIDUtils.getUUID());
                picture.setAlt("m" + "_" + UUIDUtils.getUUID());
                picture.setSrc(urls[i]);
                picture.setThumb(urls[i]);
                pictures.add(picture);
            }
        }
        return pictures;
    }

    @Override
    public ComplaintBO complaint2ComplaintBO(Complaint complaint) throws Exception {
        ComplaintBO complaintBO = new ComplaintBO();
        Manager manager = managerDao.getManagerById(complaint.getManagerId());
        if (manager != null) {
            complaintBO.setManagerTel(manager.getManagerTel());
            complaintBO.setManagerName(manager.getManagerName());
        } else {
            complaintBO.setManagerName("");
            complaintBO.setManagerTel("");
        }
        complaintBO.setId(complaint.getId());
        complaintBO.setUserId(complaint.getUserId());
        complaintBO.setComplaintDate(complaint.getDate());
        complaintBO.setMessageContent(complaint.getMessageContent());
        complaintBO.setComplaintPicture(complaint.getPicture() == null ? "" : complaint.getPicture());
        complaintBO.setManagerId(complaint.getManagerId() == null ? "" : complaint.getManagerId());
        complaintBO.setManagerResponse(complaint.getManagerResponse() == null ? "" : complaint.getManagerResponse());
        complaintBO.setResponseDate(complaint.getResponseDate() == null ? "" : complaint.getResponseDate());
        complaintBO.setResult(complaint.getResult() == null ? "" : complaint.getResult());
        complaintBO.setStatus(complaint.getStatus());
        complaintBO.setCategory(complaint.getCategory());
        System.out.println(complaintBO);
        return complaintBO;
    }

    /**
     * 获取用户未读但是管理员已经回复的投诉/建议的数量
     * @Date 2021/4/19 20:03
     * @param userId
     * @return int
     **/
    @Override
    public int getUserUnreadComplaintReply(String userId) throws Exception {
        return complaintDao.countUserUnreadComplaintReply(userId, null, "已处理");
    }
}
