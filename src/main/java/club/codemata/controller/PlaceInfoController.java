package club.codemata.controller;

import club.codemata.bo.PlaceInfoBO;
import club.codemata.entity.PlaceInfo;
import club.codemata.service.IPlaceInfoService;
import club.codemata.utils.ImageUtils;
import club.codemata.vo.MessageVO;
import club.codemata.vo.PlaceInfoVO;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
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
 * @ClassName PlaceInfoController
 * @Description 场地信息控制器
 * @createTime 2021/04/06 14:40:00
 */
@Controller
@RequestMapping("/PlaceInfo/")
public class PlaceInfoController {
    private IPlaceInfoService placeInfoService;

    @Autowired
    @Qualifier("PlaceInfoService")
    public void setPlaceInfoService(IPlaceInfoService placeInfoService) {
        this.placeInfoService = placeInfoService;
    }

    /**
     * 将一张图片上传到服务端
     * @Date 2021/4/6 16:09
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @param pictures 需要保存的图片
     * @return club.codemata.vo.MessageVO
     **/
    @RequestMapping("uploadPlaceInfoPicture.do")
    @ResponseBody
    public MessageVO doUploadPlaceInfoPictures(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @Param("pictures") MultipartFile pictures) {
        String picPath = null;
        MessageVO messageVO = new MessageVO();
        try {
            picPath = ImageUtils.upload(request, pictures, "场地照片");
            messageVO.setMsgId(1);
            messageVO.setMsgContent(picPath);
        } catch (IOException e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("上传图片时发生了异常");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    /**
     * 新增一条场地信息
     * @Date 2021/4/6 16:11
     * @param placeInfo 新添加的场地信息
     * @return club.codemata.vo.MessageVO
     **/
    @RequestMapping("savePlaceInfo.do")
    @ResponseBody
    public MessageVO doSavePlaceInfo(@Param("placeInfo") String placeInfo) {
        MessageVO messageVO = new MessageVO();
        try {
            PlaceInfo pi = JSON.parseObject(placeInfo, PlaceInfo.class);
            System.out.println(pi);
            int res = placeInfoService.savePlaceInfo(pi);
            // 如果res大于0 则证明成功新增了一条场地信息
            if (res > 0) {
                messageVO.setMsgId(res);
                messageVO.setMsgContent("成功新增了" + res + "条信息!");
            } else if (res == 0) {
                messageVO.setMsgId(res);
                messageVO.setMsgContent("操作成功,但未新增信息!");
            }
        } catch (Exception exception) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("新增信息时发生了异常!");
            exception.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    /**
     * 根据场地编号删除一条场地信息
     * @Date 2021/4/6 16:11
     * @param placeId 被删除场地的编号
     * @return club.codemata.vo.MessageVO
     **/
    @RequestMapping("removePlaceInfo.do")
    @ResponseBody
    public MessageVO doRemovePlaceInfo(@Param("placeId") int placeId) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = placeInfoService.removePlaceInfo(placeId);
            // 如果删除结果res大于0则说明成功删除了一条记录
            if (res > 0) {
                messageVO.setMsgId(res);
                messageVO.setMsgContent("成功删除了" + res + "条信息!");
            } else if (res == 0) {    // res等于0 则只是操作成功 但是没有对数据造成影响
                messageVO.setMsgId(res);
                messageVO.setMsgContent("操作成功,但未对数据造成影响!");
            }
        } catch (Exception exception) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("删除时发生了异常!");
            exception.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    /**
     * 更新一条场地信息
     * @Date 2021/4/6 16:12
     * @param placeInfo 更新后的场地信息
     * @return club.codemata.vo.MessageVO
     **/
    @RequestMapping("updatePlaceInfo.do")
    @ResponseBody
    public MessageVO doUpdatePlaceInfo(@Param("placeInfo") String placeInfo) {
        MessageVO messageVO = new MessageVO();
        try {
            PlaceInfo pi = JSON.parseObject(placeInfo, PlaceInfo.class);
            int res = placeInfoService.updatePlaceInfo(pi);
            // 更新结果res大于0 则表示成功更新了记录
            if (res > 0) {
                messageVO.setMsgId(res);
                messageVO.setMsgContent("成功更新了" + res + "条信息");
            } else if (res == 0) {
                messageVO.setMsgId(res);
                messageVO.setMsgContent("操作成功,但未对数据造成影响");
            }
        } catch (Exception e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("更新数据时发生了异常");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    /**
     * 根据场地属性及其对应值(分页)查询场地信息
     * @Date 2021/4/6 16:12
     * @param limit 每页限制条数
     * @param page 当前为第几页
     * @param property 属性--场地编号,管理员,使用状态
     * @param value 属性对应值
     * @return club.codemata.vo.PlaceInfoVO
     **/
    @RequestMapping("getPlaceInfos.do")
    @ResponseBody
    public PlaceInfoVO doGetPlaceInfos(@RequestParam(value = "limit", required = false) int limit,
                                       @RequestParam(value = "page", required = false) int page,
                                       @RequestParam(value = "property", required = false) String property,
                                       @RequestParam(value = "value", required = false) Object value) {
        PlaceInfoVO placeInfoVO = new PlaceInfoVO();
        try {
            PageHelper.startPage(page, limit);
            List<PlaceInfoBO> placeInfos = placeInfoService.getPlaceInfos(property, value);
            PageInfo<PlaceInfoBO> pageInfo = new PageInfo<>(placeInfos);
            placeInfoVO.setPlaceInfos(pageInfo.getList());
            placeInfoVO.setCount(placeInfoService.count(property, value));
            placeInfoVO.setCode(0);
            placeInfoVO.setMsg("共查询到" + placeInfoVO.getCount() + "条信息!");
        } catch (Exception e) {
            placeInfoVO.setCode(-1);
            placeInfoVO.setMsg("发生了异常!");
            e.printStackTrace();
        } finally {
            return placeInfoVO;
        }
    }

    @RequestMapping(value = "getPlaceInfoById.do")
    @ResponseBody
    public PlaceInfoVO doGetPlaceInfoById(@RequestParam("placeId") int placeId) {
        PlaceInfoVO placeInfoVO = new PlaceInfoVO();
        List<PlaceInfoBO> placeInfo = null;
        try {
            placeInfo = placeInfoService.getPlaceInfoById(placeId);
            placeInfoVO.setPlaceInfos(placeInfo);
            placeInfoVO.setCode(0);
            placeInfoVO.setCount(1);
            placeInfoVO.setMsg("查询成功!");
        } catch (Exception e) {
            placeInfoVO.setCode(-1);
            placeInfoVO.setCount(0);
            placeInfoVO.setMsg("发生了异常!");
            e.printStackTrace();
        } finally {
            return placeInfoVO;
        }
    }

    /**
     * 根据属性区间值(分页)查询场地信息
     * @Date 2021/4/6 16:16
     * @param limit 每页多少条记录
     * @param page 当前为第几页
     * @param property 属性--面积,价格
     * @param lowerValue 区间最小值
     * @param higherValue 区间最大值
     * @return club.codemata.vo.PlaceInfoVO
     **/
    @RequestMapping("getPlaceInfosByRange.do")
    @ResponseBody
    public PlaceInfoVO doGetPlaceInfosByRange(@RequestParam(value = "limit", required = false) int limit,
                                              @RequestParam(value = "page", required = false) int page,
                                              @RequestParam(value = "property", required = false) String property,
                                              @RequestParam(value = "lowerValue", required = false) int lowerValue,
                                              @RequestParam(value = "higherValue", required = false) int higherValue) {
        PlaceInfoVO placeInfoVO = new PlaceInfoVO();
        try {
            PageHelper.startPage(page, limit);
            List<PlaceInfoBO> placeInfos = placeInfoService.getPlaceInfos(property, lowerValue, higherValue);
            PageInfo<PlaceInfoBO> pageInfo = new PageInfo<>(placeInfos);
            placeInfoVO.setPlaceInfos(pageInfo.getList());
            placeInfoVO.setCode(0);
            placeInfoVO.setCount(placeInfoService.count(property, lowerValue, higherValue));
            placeInfoVO.setMsg("成功查询到" + placeInfoVO.getCount() + "条记录!");
        } catch (Exception e) {
            placeInfoVO.setCode(-1);
            placeInfoVO.setMsg("查询时发生了异常!");
            e.printStackTrace();
        } finally {
            return placeInfoVO;
        }
    }

    /**
     * 将数据库中的所有场地信息查出,返回给前端,用于前端导出到Excel文件中
     * @Date 2021/4/6 16:36
     * @return club.codemata.vo.PlaceInfoVO
     **/
    @RequestMapping("exportAllPlaceInfos.do")
    @ResponseBody
    public PlaceInfoVO doExportAllPlaceInfos() {
        PlaceInfoVO placeInfoVO = new PlaceInfoVO();
        try {
            List<PlaceInfoBO> placeInfos = placeInfoService.getPlaceInfos(null, null);
            placeInfoVO.setPlaceInfos(placeInfos);
            placeInfoVO.setMsg("成功查询到" + placeInfoVO.getPlaceInfos().size() + "条记录!");
            placeInfoVO.setCode(0);
            placeInfoVO.setCount(placeInfos.size());
        } catch (Exception e) {
            placeInfoVO.setCode(-1);
            placeInfoVO.setMsg("查询时发生了异常!");
            e.printStackTrace();
        } finally {
            return placeInfoVO;
        }
    }

    /**
     * 根据价格区间或者面积区间进行场地信息查询 并进行分页
     * @Date 2021/4/13 20:36
     * @param property 属性--价格，面积
     * @param lowerValue 最小值
     * @param higherValue 最大值
     * @return club.codemata.vo.PlaceInfoVO
     **/
    @RequestMapping("searchByRange.do")
    @ResponseBody
    public PlaceInfoVO doSearchByRange(
                                       @RequestParam("property") String property,
                                       @RequestParam("lowerValue") int lowerValue,
                                       @RequestParam("higherValue") int higherValue) {
        PlaceInfoVO placeInfoVO = new PlaceInfoVO();
        try {
            List<PlaceInfoBO> placeInfos = placeInfoService.getPlaceInfos(property, lowerValue, higherValue);
            placeInfoVO.setPlaceInfos(placeInfos);
            placeInfoVO.setCode(0);
            placeInfoVO.setCount(placeInfoService.count(property, lowerValue, higherValue));
            placeInfoVO.setMsg("成功查询到" + placeInfoVO.getCount() + "条记录");
        } catch (Exception e) {
            placeInfoVO.setMsg("发生了异常!");
            placeInfoVO.setCode(-1);
            placeInfoVO.setCount(0);
            e.printStackTrace();
        } finally {
            return placeInfoVO;
        }
    }
}
