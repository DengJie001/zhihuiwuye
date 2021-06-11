package club.codemata.controller;

import club.codemata.bo.RepairInfoBO;
import club.codemata.entity.PropertyRepairInfo;
import club.codemata.service.IPropertyRepairService;
import club.codemata.vo.RepairInfoVO;
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
 * @ClassName PropertyRepairController
 * @Description TODO
 * @createTime 2021/04/21 12:49:00
 */
@Controller
@RequestMapping(value = "/PropertyRepair/")
public class PropertyRepairController {
    private IPropertyRepairService repairService;

    @Autowired
    @Qualifier("PropertyRepairService")
    public void setRepairService(IPropertyRepairService repairService) {
        this.repairService = repairService;
    }

    @RequestMapping(value = "addOrder.do")
    @ResponseBody
    public HashMap<String, Object> doAddOrder(@RequestParam("userId") String userId,
                              @RequestParam("workerId") String workerId,
                              @RequestParam("repairDescription") String repairDescription) {
        HashMap<String, Object> data = new HashMap<>();
        try {
            PropertyRepairInfo res = repairService.addOrder(userId, workerId, repairDescription);
            if (res != null) {
                data.put("status", "success");
                data.put("data", res);
            } else {
                data.put("status", "error");
            }
        } catch (Exception e) {
            data.put("status", "exception");
            e.printStackTrace();
        } finally {
            return data;
        }
    }

    @RequestMapping(value = "getRepairInfos.do")
    @ResponseBody
    public RepairInfoVO doGetRepairInfos(@RequestParam(value = "page") int page,
                                         @RequestParam(value = "limit") int limit,
                                         @RequestParam(value = "property") String property,
                                         @RequestParam(value = "value") String value) {
        RepairInfoVO repairInfoVO = new RepairInfoVO();
        try {
            PageHelper.startPage(page, limit);
            List<RepairInfoBO> repairInfos = repairService.getRepairInfos(property, value);
            PageInfo<RepairInfoBO> pageInfo = new PageInfo<>(repairInfos);
            repairInfoVO.setRepairInfos(pageInfo.getList());
            repairInfoVO.setCode(0);
            repairInfoVO.setCount(repairService.count(property, value));
            repairInfoVO.setMsg("成功查询到" + repairInfoVO.getCount() + "条记录");
        } catch (Exception e) {
            repairInfoVO.setCode(-1);
            repairInfoVO.setCount(0);
            repairInfoVO.setMsg("发生异常");
            e.printStackTrace();
        } finally {
            return repairInfoVO;
        }
    }

    @RequestMapping(value = "userDelete.do")
    @ResponseBody
    public HashMap<String, Object> doUserDelete(@RequestParam(value = "id") String id,
                                                @RequestParam(value = "type") String type) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = repairService.userDelete(id, type);
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
