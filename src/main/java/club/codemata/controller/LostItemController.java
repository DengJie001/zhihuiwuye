package club.codemata.controller;

import club.codemata.bo.LostItemBO;
import club.codemata.service.ILostItemService;
import club.codemata.utils.ImageUtils;
import club.codemata.vo.LostItemVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LostItemController
 * @Description 失物招领控制器
 * @createTime 2021/04/29 00:10:00
 */
@Controller
@RequestMapping(value = "/LostItem/")
public class LostItemController {
    private ILostItemService itemService;

    @Autowired
    @Qualifier(value = "LostItemService")
    public void setItemService(ILostItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(value = "uploadPicture.do")
    @ResponseBody
    public String doUploadPicture(@RequestParam("picture")MultipartFile picture,
                                  HttpServletRequest request) {
        String path = "error";
        try {
            path = ImageUtils.wxUploadImage(request, picture, "失物招领");
        } catch (IOException e) {
            path = "exception";
            e.printStackTrace();
        } finally {
            return path;
        }
    }

    @RequestMapping(value = "addLostItem.do")
    @ResponseBody
    public HashMap<String, Object> doAddLostItem(@RequestParam("userId") String userId,
                                                 @RequestParam("itemDescription") String itemDescription,
                                                 @RequestParam("itemCategory") String itemCategory,
                                                 @RequestParam("itemPicture") String itemPicture) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = itemService.addLostItem(userId, itemDescription, itemCategory, itemPicture);
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

    @RequestMapping(value = "getAllLostItems.do")
    @ResponseBody
    public HashMap<String, Object> doGetAllLostItems(@RequestParam("limit") int limit,
                                                     @RequestParam("page") int page) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            LostItemVO vo = new LostItemVO();
            PageHelper.startPage(page, limit);
            List<LostItemBO> items = itemService.getLostItems(null, null);
            PageInfo<LostItemBO> pageInfo = new PageInfo<>(items);
            vo.setCode(0);
            vo.setItems(pageInfo.getList());
            vo.setCount(itemService.count(null, null));
            vo.setMsg("成功查询到" + vo.getCount() + "条记录");
            info.put("status", "success");
            info.put("info", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "getUserAllLostItems.do")
    @ResponseBody
    public HashMap<String, Object> doGetUserAllLostItems(@RequestParam("limit") int limit,
                                                         @RequestParam("page") int page,
                                                         @RequestParam("userId") String userId) {
        HashMap<String, Object> info = new HashMap<>();
        LostItemVO vo = new LostItemVO();
        try {
            PageHelper.startPage(page, limit);
            List<LostItemBO> items = itemService.getLostItems("用户ID", userId);
            PageInfo<LostItemBO> pageInfo = new PageInfo<>(items);
            vo.setItems(pageInfo.getList());
            vo.setCode(0);
            vo.setCount(itemService.count("用户ID", userId));
            vo.setMsg("成功查询到" + vo.getCount() + "条记录!");
            info.put("status", "success");
            info.put("info", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            vo.setMsg("服务端异常");
            vo.setCount(0);
            vo.setCode(-1);
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "userDeleteLostItem.do")
    @ResponseBody
    public HashMap<String, Object> doUserDeleteLostItem(@RequestParam("id") String id,
                                                        @RequestParam(value = "complaintCategory", required = false) String complaintCategory,
                                                        @RequestParam(value = "itemCategory", required = false) String itemCategory,
                                                        @RequestParam(value = "itemDate", required = false) String itemDate) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = itemService.removeLostItem(id, complaintCategory, itemCategory, itemDate);
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

    @RequestMapping(value = "modifyItemStatus.do")
    @ResponseBody
    public HashMap<String, Object> doModifyItemStatus(@RequestParam("itemId") String itemId) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = itemService.modifyLostItemStatus(itemId);
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
}
