package club.codemata.controller;

import club.codemata.bo.UtilityBillBO;
import club.codemata.service.IUtilityBillService;
import club.codemata.vo.UtilityBillVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName UtilityBillController
 * @Description 物业账单控制器
 * @createTime 2021/04/26 03:48:00
 */
@Controller
@RequestMapping("/bill/")
public class UtilityBillController {
    private IUtilityBillService billService;

    @Autowired
    @Qualifier(value = "UtilityBillService")
    public void setBillService(IUtilityBillService billService) {
        this.billService = billService;
    }

    @RequestMapping("addBill.do")
    @ResponseBody
    public HashMap<String, Object> doAddBill(@RequestParam("billFigure") float billFigure,
                                               @RequestParam("areaId") String areaId,
                                               @RequestParam("unitId") String unitId,
                                               @RequestParam("roomId") String roomId,
                                               @RequestParam("managerId") String managerId,
                                               @RequestParam("billCategory") String billCategory) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int insertRes = billService.addUtilityBill(billFigure, areaId, unitId, roomId, managerId, billCategory);
            if (insertRes > 0) {
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

    @RequestMapping(value = "modifyBill.do")
    @ResponseBody
    public HashMap<String, Object> doModifyBill(@RequestParam("billId") String billId,
                                                @RequestParam("billFigure") float billFigure,
                                                @RequestParam("billCategory") String billCategory) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int updateRes = billService.modifyUtilityBill(billFigure, billCategory, billId);
            if (updateRes > 0) {
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

    @RequestMapping(value = "managerGetBills.do")
    @ResponseBody
    public HashMap<String, Object> doManagerGetBills(@RequestParam("limit") int limit,
                                                     @RequestParam("page") int page,
                                                     @RequestParam("property") String property,
                                                     @RequestParam("value") String value) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            UtilityBillVO vo = new UtilityBillVO();
            PageHelper.startPage(page, limit);
            List<UtilityBillBO> bills = billService.getUtilityBills(property, value, null);
            PageInfo<UtilityBillBO> pageInfo = new PageInfo<>(bills);
            vo.setBills(pageInfo.getList());
            vo.setCode(0);
            vo.setCount(billService.count(property, value, null));
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

    @RequestMapping(value = "managerGetAllBills.do")
    @ResponseBody
    public HashMap<String, Object> doManagerGetAllBills(@RequestParam("limit") int limit,
                                                        @RequestParam("page") int page) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            UtilityBillVO vo = new UtilityBillVO();
            PageHelper.startPage(page, limit);
            List<UtilityBillBO> bills = billService.getUtilityBills(null, null, null);
            PageInfo<UtilityBillBO> pageInfo = new PageInfo<>(bills);
            vo.setBills(pageInfo.getList());
            vo.setCount(billService.count(null, null, null));
            vo.setCode(0);
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

    @RequestMapping(value = "exportAllBills.do")
    @ResponseBody
    public HashMap<String, Object> doExportAllBills() {
        HashMap<String, Object> info = new HashMap<>();
        try {
            UtilityBillVO vo = new UtilityBillVO();
            List<UtilityBillBO> bills = billService.getUtilityBills(null, null, null);
            vo.setBills(bills);
            vo.setMsg("成功导出" + bills.size() + "条账单");
            vo.setCode(0);
            vo.setCount(bills.size());
            info.put("status", "success");
            info.put("info", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }


    @RequestMapping(value = "userGetAllBills.do")
    @ResponseBody
    public HashMap<String, Object> doUserGetAllBills(@RequestParam("areaId") String areaId,
                                                     @RequestParam("unitId") String unitId,
                                                     @RequestParam("roomId") String roomId,
                                                     @RequestParam("limit") int limit,
                                                     @RequestParam("page") int page) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            UtilityBillVO vo = new UtilityBillVO();
            PageHelper.startPage(page, limit);
            List<UtilityBillBO> bills = billService.getUtilityBillByAddress(areaId, unitId, roomId, "未缴费");
            PageInfo<UtilityBillBO> pageInfo = new PageInfo<>(bills);
            vo.setBills(pageInfo.getList());
            vo.setCode(0);
            vo.setCount(billService.countByAddress(areaId, unitId, roomId, "未缴费"));
            vo.setMsg("成功查询到" + vo.getCount() + "条账单信息");
            info.put("status", "success");
            info.put("info", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "userGetPaidUtilityBills.do")
    @ResponseBody
    public HashMap<String, Object> doUserGetPaidUtilityBills(@RequestParam("areaId") String areaId,
                                                             @RequestParam("unitId") String unitId,
                                                             @RequestParam("roomId") String roomId,
                                                             @RequestParam("limit") int limit,
                                                             @RequestParam("page") int page) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            UtilityBillVO vo = new UtilityBillVO();
            PageHelper.startPage(page, limit);
            List<UtilityBillBO> bills = billService.getUtilityBillByAddress(areaId, unitId, roomId, "已缴费");
            PageInfo<UtilityBillBO> pageInfo = new PageInfo<>(bills);
            vo.setBills(pageInfo.getList());
            vo.setCode(0);
            vo.setCount(billService.countByAddress(areaId, unitId, roomId, "已缴费"));
            vo.setMsg("成功查询到" + vo.getCount() + "条账单信息");
            info.put("status", "success");
            info.put("info", vo);
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "userGetUnpaidBills.do")
    @ResponseBody
    public HashMap<String, Object> doUserGetUnpaidBills(@RequestParam("areaId") String areaId,
                                                        @RequestParam("unitId") String unitId,
                                                        @RequestParam("roomId") String roomId) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            List<UtilityBillBO> bills = billService.getUtilityBillByAddress(areaId, unitId, roomId, "未缴费");
            info.put("status", "success");
            info.put("unpaid", bills.size());
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }

    @RequestMapping(value = "urgePayBill.do")
    @ResponseBody
    public HashMap<String, Object> doUrgePayBill(@RequestParam("areaId") String areaId,
                                                 @RequestParam("unitId") String unitId,
                                                 @RequestParam("roomId") String roomId,
                                                 @RequestParam("date") String date,
                                                 @RequestParam("billCategory") String billCategory,
                                                 @RequestParam("userId") String userId) {
        HashMap<String, Object> info = new HashMap<>();
        try {
            int res = billService.urgePayBill(areaId, unitId, roomId, date, billCategory, userId);
            info.put("status", "success");
        } catch (Exception e) {
            info.put("status", "exception");
            e.printStackTrace();
        } finally {
            return info;
        }
    }
}
