package club.codemata.controller;

import club.codemata.bo.LostItemComplaintBO;
import club.codemata.service.ILostItemComplaintService;
import club.codemata.utils.UUIDUtils;
import club.codemata.vo.LostItemComplaintVO;
import club.codemata.vo.PictureVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LostItemComplaintController
 * @Description TODO
 * @createTime 2021/05/07 17:45:00
 */
@Controller
@RequestMapping(value = "/LostItemComplaint/")
public class LostItemComplaintController {
    private ILostItemComplaintService complaintService;

    @Autowired
    @Qualifier(value = "LostItemComplaintService")
    public void setComplaintService(ILostItemComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @RequestMapping(value = "addComplaint.do")
    @ResponseBody
    public HashMap<String, Object> doAddComplaint(@RequestParam("complaintCategory") String complaintCategory,
                                                  @RequestParam("lostItemId") String lostItemId) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = complaintService.addComplaint(lostItemId, complaintCategory);
            if (res > 0) {
                info.put("status", "success");
            } else {
                info.put("status", "error");
            }
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "getAllLostItemComplaints.do")
    @ResponseBody
    public HashMap<String, Object> doGetAllLostItemComplaints(@RequestParam("limit") int limit,
                                                              @RequestParam("page") int page) {
        HashMap<String, Object> info = new HashMap<>();
        LostItemComplaintVO vo = new LostItemComplaintVO();
        try {
            PageHelper.startPage(page, limit);
            List<LostItemComplaintBO> complaints = complaintService.getAllLostItemComplaints();
            PageInfo<LostItemComplaintBO> pageInfo = new PageInfo<>(complaints);
            vo.setComplaints(pageInfo.getList());
            vo.setCode(0);
            vo.setCount(complaintService.count());
            vo.setMsg("成功查询到" + vo.getCount() + "条记录!");
            info.put("status", "success");
            info.put("info", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "getPictureJson.do")
    @ResponseBody
    public HashMap<String, Object> doGetPictureJson(@RequestParam("lostItemId") String lostItemId) {
        HashMap<String, Object> info = new HashMap<>();
        PictureVO vo = new PictureVO();
        try {
            vo.setTitle("失物招领图片");
            vo.setId(UUIDUtils.getUUID());
            vo.setData(complaintService.getPictures(lostItemId));
            vo.setStart(0);
            info.put("status", "success");
            info.put("json", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }
}
