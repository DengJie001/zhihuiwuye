package club.codemata.controller;

import club.codemata.bo.ComplaintBO;
import club.codemata.model.Picture;
import club.codemata.service.IComplaintService;
import club.codemata.utils.ImageUtils;
import club.codemata.utils.UUIDUtils;
import club.codemata.vo.ComplaintVO;
import club.codemata.vo.MessageVO;
import club.codemata.vo.PictureVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ComplaintController
 * @Description 用户投诉建议控制器
 * @createTime 2021/04/17 01:05:00
 */
@Controller
@RequestMapping("/complaint/")
public class ComplaintController {
    private IComplaintService complaintService;

    @Autowired
    @Qualifier("ComplaintService")
    public void setComplaintService(IComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    /**
     * 上传投诉建议的图片
     * @Date 2021/4/17 14:14
     * @param image 图片文件
     * @param request 本次请求对象
     * @return java.lang.String
     **/
    @RequestMapping(value = "uploadComplaintImages.do")
    @ResponseBody
    public String doUploadComplaintImages(@RequestParam("image") MultipartFile image,
                                             HttpServletRequest request) {
        String path = "error";
        try {
            path = ImageUtils.wxUploadImage(request, image, "投诉建议");
        } catch (IOException e) {
            path = "error";
            e.printStackTrace();
        } finally {
            return path;
        }
    }

    @RequestMapping(value = "addComplaint.do")
    @ResponseBody
    public MessageVO doAddComplaint(@RequestParam("userId") String userId,
                                    @RequestParam("category") String category,
                                    @RequestParam("content") String content,
                                    @RequestParam("pictures") String pictures) {
        System.out.println("照片:" + pictures);
        System.out.println("结果:" + pictures == null);
        System.out.println("比较:" + pictures == "");
        MessageVO messageVO = new MessageVO();
        try {
            int res = complaintService.addComplaint(userId, category, pictures, content);
            messageVO.setMsgId(res);
            if (res > 0) {
                messageVO.setMsgContent("提交成功");
            } else {
                messageVO.setMsgContent("提交失败");
            }
        } catch (Exception e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("发生了异常!");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "deleteComplaint.do")
    @ResponseBody
    public MessageVO doDeleteComplaint(@RequestParam(value = "id") String id) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = complaintService.deleteComplaint(id);
            messageVO.setMsgId(res);
            if (res > 0) {
                messageVO.setMsgContent("删除成功");
            } else if (res == 0) {
                messageVO.setMsgContent("删除失败");
            }
        } catch (Exception e) {
            messageVO.setMsgContent("发生了异常!");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "getAllComplaints.do")
    @ResponseBody
    public ComplaintVO doGetAllComplaints(@RequestParam("page") int page,
                                          @RequestParam("limit") int limit) {
        ComplaintVO complaintVO = new ComplaintVO();
        try {
            PageHelper.startPage(page, limit);
            List<ComplaintBO> complaints = complaintService.getComplaints(null, null);
            PageInfo<ComplaintBO> pageInfo = new PageInfo<>(complaints);
            complaintVO.setComplaints(pageInfo.getList());
            complaintVO.setCode(0);
            complaintVO.setCount(complaintService.count(null, null));
            complaintVO.setMsg("成功查询到" + complaintVO.getCount() + "条记录!");
        } catch (Exception e) {
            complaintVO.setMsg("发生了异常");
            complaintVO.setCode(-1);
            complaintVO.setCount(0);
            e.printStackTrace();
        } finally {
            return complaintVO;
        }
    }

    @RequestMapping(value = "userGetAllComplaints.do")
    @ResponseBody
    public ComplaintVO doUserGetAllComplaints(@RequestParam(value = "userId") String value,
                                              @RequestParam(value = "page") int page,
                                              @RequestParam(value = "limit") int limit) {
        ComplaintVO complaintVO =new ComplaintVO();
        try {
            PageHelper.startPage(page, limit);
            List<ComplaintBO> complaints = complaintService.getComplaints("用户ID", value);
            PageInfo<ComplaintBO> pageInfo = new PageInfo<>(complaints);
            complaintVO.setComplaints(pageInfo.getList());
            complaintVO.setCount(complaintService.count("用户ID", value));
            complaintVO.setCode(0);
            complaintVO.setMsg("成功查询到" + complaintVO.getCount() + "条记录");
        } catch (Exception e) {
            complaintVO.setMsg("发生了异常");
            complaintVO.setCode(-1);
            complaintVO.setCount(0);
            e.printStackTrace();
        } finally {
            return complaintVO;
        }
    }

    @RequestMapping(value = "managerReply.do")
    @ResponseBody
    public MessageVO doManagerReply(@RequestParam("managerResponse") String managerResponse,
                                    @RequestParam("managerId") String managerId,
                                    @RequestParam("complaintId") String complaintId) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = complaintService.updateComplaintManagerResponse(complaintId, managerId, managerResponse);
            messageVO.setMsgId(res);
            if (res > 0) {
                messageVO.setMsgContent("回复成功");
            } else if (res == -1) {
                messageVO.setMsgContent("回复失败,可能其他管理员已经回复过,请刷新页面");
            } else {
                messageVO.setMsgContent("回复失败");
            }
        } catch (Exception e) {
            messageVO.setMsgContent("发生了异常");
            messageVO.setMsgId(-2);
            e.printStackTrace();
        }
        finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "exportAllComplaints.do")
    @ResponseBody
    public ComplaintVO doExportAllComplaints() {
        ComplaintVO complaintVO = new ComplaintVO();
        try {
            List<ComplaintBO> complaints = complaintService.getComplaints(null, null);
            complaintVO.setComplaints(complaints);
            complaintVO.setCode(0);
            complaintVO.setCount(complaintService.count(null, null));
            complaintVO.setMsg("成功导出" + complaintVO.getCount() + "条记录!");
        } catch (Exception e) {
            complaintVO.setCode(-1);
            complaintVO.setCount(0);
            complaintVO.setMsg("发生了异常!");
            e.printStackTrace();
        } finally {
            return complaintVO;
        }
    }

    @RequestMapping(value = "getUntreatedComplaints.do")
    @ResponseBody
    public ComplaintVO doGetUntreatedComplaints(@RequestParam("limit") int limit,
                                                @RequestParam("page") int page) {
        ComplaintVO complaintVO = new ComplaintVO();
        try {
            PageHelper.startPage(page, limit);
            List<ComplaintBO> complaints = complaintService.getComplaints("处理状态", "未处理");
            PageInfo<ComplaintBO> pageInfo = new PageInfo<>(complaints);
            complaintVO.setComplaints(pageInfo.getList());
            complaintVO.setCount(complaintService.count("处理状态", "未处理"));
            complaintVO.setCode(0);
            complaintVO.setMsg("成功查询到" + complaintVO.getCount() + "条记录");
        } catch (Exception e) {
            complaintVO.setCode(-1);
            complaintVO.setCount(0);
            complaintVO.setMsg("发生了异常");
            e.printStackTrace();
        } finally {
            return complaintVO;
        }
    }

    @RequestMapping(value = "getDissatisfactionComplaints.do")
    @ResponseBody
    public ComplaintVO doGetDissatisfactionComplaints(@RequestParam("limit") int limit,
                                                      @RequestParam("page") int page) {
        ComplaintVO complaintVO = new ComplaintVO();
        try {
            PageHelper.startPage(page, limit);
            List<ComplaintBO> complaints = complaintService.getComplaints("用户意见", "不满意");
            PageInfo<ComplaintBO> pageInfo = new PageInfo<>(complaints);
            complaintVO.setComplaints(pageInfo.getList());
            complaintVO.setCode(0);
            complaintVO.setCount(complaintService.count("用户意见", "不满意"));
            complaintVO.setMsg("成功查询到" + complaintVO.getCount() + "条记录!");
        } catch (Exception e) {
            complaintVO.setMsg("发生了异常!");
            complaintVO.setCode(-1);
            complaintVO.setCount(0);
            e.printStackTrace();
        } finally {
            return complaintVO;
        }
    }

    @RequestMapping(value = "managerSearchComplaints.do")
    @ResponseBody
    public ComplaintVO doManagerSearchComplaints(@RequestParam("page") int page,
                                                 @RequestParam("limit") int limit,
                                                 @RequestParam("property") String property,
                                                 @RequestParam("value") String value) {
        ComplaintVO complaintVO = new ComplaintVO();
        try {
            PageHelper.startPage(page, limit);
            List<ComplaintBO> complaints = complaintService.getComplaints(property, value);
            PageInfo<ComplaintBO> pageInfo = new PageInfo<>(complaints);
            complaintVO.setComplaints(pageInfo.getList());
            complaintVO.setCount(complaintService.count(property, value));
            complaintVO.setCode(0);
            complaintVO.setMsg("成功查询到" + complaintVO.getCount() + "条记录");
        } catch (Exception e) {
            complaintVO.setCode(-1);
            complaintVO.setCount(0);
            complaintVO.setMsg("发生了异常");
            e.printStackTrace();
        } finally {
            return complaintVO;
        }
    }

    @RequestMapping(value = "getPictureJson.do")
    @ResponseBody
    public PictureVO doGetPictureJson(@RequestParam("complaintId") String complaintId) {
        System.out.println(complaintId);
        PictureVO pictureV0 = new PictureVO();
        try {
            List<Picture> pictures = complaintService.getPicturesByComplaintId(complaintId);
            pictureV0.setId(UUIDUtils.getUUID());
            pictureV0.setData(pictures);
            pictureV0.setStart(0);
            pictureV0.setTitle(UUIDUtils.getUUID());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return pictureV0;
        }
    }

    @RequestMapping(value = "getUserUnreadComplaintReply.do")
    @ResponseBody
    public MessageVO doGetUserUnreadComplaintReply(@RequestParam("userId") String userId) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = complaintService.getUserUnreadComplaintReply(userId);
            messageVO.setMsgId(res);
            if (res >= 0) {
                messageVO.setMsgContent("success!");
            }
        } catch (Exception e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("error");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "submitResult.do")
    @ResponseBody
    public MessageVO doSubmitResult(@RequestParam("complaintId") String complaintId, @RequestParam("result") String result) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = complaintService.updateComplaintResult(complaintId, result);
            messageVO.setMsgId(res);
            if (res > 0) {
                messageVO.setMsgContent("反馈成功");
            } else if (res == 0) {
                messageVO.setMsgContent("反馈失败");
            }
        } catch (Exception e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("异常!");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }
}
