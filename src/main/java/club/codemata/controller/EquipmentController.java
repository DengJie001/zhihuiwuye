package club.codemata.controller;

import club.codemata.bo.EquipmentBO;
import club.codemata.entity.Equipment;
import club.codemata.service.IEquipmentService;
import club.codemata.service.impl.EquipmentServiceImpl;
import club.codemata.utils.ImageUtils;
import club.codemata.utils.UUIDUtils;
import club.codemata.vo.EquipmentVO;
import club.codemata.vo.MessageVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName EquipmentController
 * @Description 设备资产控制层
 * @createTime 2021/03/10 09:15:00
 */
@Controller
@RequestMapping(value = "/equipment/")
public class EquipmentController {
    private IEquipmentService equipmentService;

    @Autowired
    @Qualifier(value = "EquipmentService")
    public void setEquipmentService(IEquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @RequestMapping(value = "uploadEquipmentPictures.do")
    @ResponseBody
    public MessageVO doUploadEquipmentPictures(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam("pictures") MultipartFile pictures) {
        MessageVO messageVO = new MessageVO();
        String imgPath = null;
        try {
            imgPath = ImageUtils.upload(request, pictures, "设备照片");
            messageVO.setMsgId(1);
            messageVO.setMsgContent(imgPath);
        } catch (IOException e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("上传图片时发生了异常");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "addEquipment.do")
    @ResponseBody
    public MessageVO doAddEquipment(@RequestParam("equipment") String equipment) {
        MessageVO messageVO = new MessageVO();
        Equipment equipment1 = JSON.parseObject(equipment, Equipment.class);
        equipment1.setEquipmentId(UUIDUtils.getUUID());
        System.out.println(equipment1);
        int res = equipmentService.saveEquipment(equipment1);
        if (res > 0) {
            messageVO.setMsgId(1);
            messageVO.setMsgContent("新增成功");
        } else {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("操作成功,但未新增设备信息成功");
        }
        return messageVO;
    }

    @RequestMapping(value = "getEquipmentInfosByPage.do")
    @ResponseBody
    public EquipmentVO doGetEquipmentInfosByPage(@RequestParam(required = false, value = "limit") int limit,
                                                 @RequestParam(required = false, value = "page", defaultValue = "1") int page) {
        EquipmentVO equipmentVO = new EquipmentVO();
        // 开启分页
        PageHelper.startPage(page, limit);
        List<EquipmentBO> equipments = equipmentService.getAllEquipments();
        int count = equipmentService.count(null, null);
        PageInfo<EquipmentBO> pageInfo = new PageInfo<>(equipments);

        // 组装为前端所需要的数据格式
        equipmentVO.setEquipments(pageInfo.getList());
        equipmentVO.setCode(0);
        equipmentVO.setCount(count);
        equipmentVO.setMsg("查询成功");
        return equipmentVO;
    }

    @RequestMapping(value = "exportAllEquipmentInfos.do")
    @ResponseBody
    public List<EquipmentBO> doExportAllEquipmentInfos() {
        List<EquipmentBO> allEquipments = equipmentService.getAllEquipments();
        return allEquipments;
    }

    @RequestMapping(value = "removeEquipmentInfo.do")
    @ResponseBody
    public MessageVO doRemoveEquipmentInfo(@RequestParam("equipmentId") String equipmentId) {
        MessageVO messageVO = new MessageVO();
        int res = equipmentService.removeEquipmentById(equipmentId);
        if (res > 0) {  // 删除结果res大于0则表明成功删除对应id的记录
            messageVO.setMsgId(res);
            messageVO.setMsgContent("成功删除了" + res + "条记录");
        } else {
            messageVO.setMsgId(res);
            messageVO.setMsgContent("执行删除操作失败");
        }
        return messageVO;
    }

    @RequestMapping(value = "modifyEquipmentInfo.do")
    @ResponseBody
    public MessageVO doModifyEquipment(@RequestParam("equipment") String equipment) {
        MessageVO messageVO = new MessageVO();
        Equipment equipment1 = JSON.parseObject(equipment, Equipment.class);
        int res = equipmentService.updateEquipment(equipment1);
        if (res > 0) {
            messageVO.setMsgId(res);
            messageVO.setMsgContent("更新设备信息成功");
        } else {
            messageVO.setMsgId(res);
            messageVO.setMsgContent("更新设备信息失败");
        }
        return messageVO;
    }

    @RequestMapping(value = "search.do")
    @ResponseBody
    public EquipmentVO doSearch(@RequestParam("property") String property,
                                @RequestParam("keyWords") String keyWords,
                                @RequestParam("limit") int limit,
                                @RequestParam("page") int page) {
        EquipmentVO equipmentVO = new EquipmentVO();
        equipmentVO.setCode(0);
        PageHelper.startPage(page, limit);
        List<EquipmentBO> equipmentBOS = equipmentService.search(property, keyWords);
        PageInfo<EquipmentBO> pageInfo = new PageInfo<>(equipmentBOS);
        if (equipmentBOS.size() > 0) {
            equipmentVO.setEquipments(pageInfo.getList());
            equipmentVO.setCount(equipmentService.count(property, keyWords));
            System.out.println("list" + equipmentVO.getEquipments());
            System.out.println("count:" + equipmentVO.getCount());
            equipmentVO.setMsg("查询成功");
        } else {
            equipmentVO.setEquipments(equipmentBOS);
            equipmentVO.setCount(0);
            equipmentVO.setMsg("未查询到对应记录");
        }
        return equipmentVO;
    }
}
