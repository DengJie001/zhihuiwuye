package club.codemata.controller;

import club.codemata.entity.Worker;
import club.codemata.service.IWorkerService;
import club.codemata.utils.ImageUtils;
import club.codemata.vo.MessageVO;
import club.codemata.vo.WorkerVO;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName WorkerController
 * @Description 维修人员控制器
 * @createTime 2021/04/15 16:49:00
 */
@Controller
@RequestMapping("/worker/")
public class WorkerController {
    private IWorkerService workerService;

    @Autowired
    @Qualifier("WorkerService")
    public void setWorkerService(IWorkerService workerService) {
        this.workerService = workerService;
    }

    /**
     * 保存维修人员的头像
     * @Date 2021/4/15 16:57
     * @param request 本次请求
     * @param response 回应
     * @param avatar 头像文件
     * @return club.codemata.vo.MessageVO
     **/
    @RequestMapping("uploadWorkerAvatar.do")
    @ResponseBody
    public MessageVO doUploadWorkerAvatar(HttpServletRequest request,
                                          HttpServletResponse response,
                                          @RequestParam("avatar") MultipartFile avatar) {
        MessageVO messageVO = new MessageVO();
        try {
            String avatarPath = ImageUtils.upload(request, avatar, "维修人员头像");
            messageVO.setMsgContent(avatarPath);
            messageVO.setMsgId(1);
        } catch (IOException e) {
            messageVO.setMsgContent("出现了异常!");
            messageVO.setMsgId(-1);
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    /**
     * 新增加一条维修人员的信息
     * @Date 2021/4/15 16:52
     * @param workerTel 维修人员电话
     * @param workerName 维修人员的姓名
     * @param workerAvatar 维修人员头像
     * @param workerDescription 维修人员简要介绍
     * @return club.codemata.vo.MessageVO
     **/
    @RequestMapping("addWorker.do")
    @ResponseBody
    public MessageVO doAddWorker(@RequestParam(value = "workerTel") String workerTel,
                                 @RequestParam(value = "workerName") String workerName,
                                 @RequestParam(value = "workerAvatar") String workerAvatar,
                                 @RequestParam(value = "workerDescription") String workerDescription,
                                 @RequestParam(value = "gender") String gender,
                                 @RequestParam(value = "cost") int cost) {
        MessageVO messageVO = new MessageVO();
        System.out.println("cost:" + cost);
        try {
            int res = workerService.saveWorker(workerTel, workerName, workerDescription, workerAvatar, gender, cost);
            messageVO.setMsgId(res);
            if (res > 0) {
                messageVO.setMsgContent("操作成功,新增成功!");
            } else {
                messageVO.setMsgContent("操作成功,新增失败");
            }
        } catch (Exception e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("出现了异常!");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "applyRepair.do")
    @ResponseBody
    public MessageVO doApplyRepair(@RequestParam(value = "workerId", required = false) String workerId) {
        MessageVO messageVO = new MessageVO();
        try {
            boolean res = workerService.applyRepair(workerId);
            if (res) {
                messageVO.setMsgId(1);
                messageVO.setMsgContent("申请成功");
            } else {
                messageVO.setMsgContent("申请失败");
                messageVO.setMsgId(0);
            }
        } catch (Exception e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("发生了异常");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "getAllWorkers.do")
    @ResponseBody
    public WorkerVO doGetAllWorkers(@RequestParam(value = "page", required = false) int page,
                                    @RequestParam(value = "limit", required = false) int limit) {
        WorkerVO workerVO = new WorkerVO();
        try {
            PageHelper.startPage(page, limit);
            List<Worker> workers = workerService.getWorkers(null, null);
            PageInfo<Worker> pageInfo = new PageInfo<>(workers);
            workerVO.setWorkers(pageInfo.getList());
            workerVO.setCode(0);
            workerVO.setCount(workerService.count(null, null));
            workerVO.setMsg("成功查询到" + workerVO.getCount() + "条记录!");
        } catch (Exception e) {
            workerVO.setCode(-1);
            workerVO.setCount(0);
            workerVO.setMsg("发生了异常!");
            e.printStackTrace();
        } finally {
            return workerVO;
        }
    }

    /**
     * 查询所有的维修人员信息,并且传回前端用于导出
     * @Date 2021/4/16 10:19
     * @return club.codemata.vo.WorkerVO
     **/
    @RequestMapping(value = "exportAllWorkerInfos.do")
    @ResponseBody
    public WorkerVO doExportAllWorkerInfos() {
        WorkerVO workerVO = new WorkerVO();
        try {
            List<Worker> workers = workerService.getWorkers(null, null);
            workerVO.setMsg("成功导出" + workers.size() + "条记录");
            workerVO.setWorkers(workers);
            workerVO.setCount(workers.size());
            workerVO.setCode(0);
        } catch (Exception e) {
            workerVO.setCount(0);
            workerVO.setCode(-1);
            workerVO.setMsg("发生了异常!");
            e.printStackTrace();
        } finally {
            return workerVO;
        }
    }

    /**
     * 按照对应属性和关键字搜索维修人员信息<br>
     * 并且对查询结果进行分页
     * @Date 2021/4/16 10:27
     * @param page 当前为第几页
     * @param limit 每一页多少条
     * @param property 属性--评分,维修人员ID,排队人数,服务次数
     * @param value 属性对应的值
     * @return club.codemata.vo.WorkerVO
     **/
    @RequestMapping(value = "search.do")
    @ResponseBody
    public WorkerVO doSearch(@RequestParam(value = "page", required = false) int page,
                             @RequestParam(value = "limit", required = false) int limit,
                             @RequestParam(value = "property", required = false) String property,
                             @RequestParam(value = "value", required = false) Object value) {
        WorkerVO workerVO = new WorkerVO();
        try {
            PageHelper.startPage(page, limit);
            List<Worker> workers = workerService.getWorkers(property, value);
            PageInfo<Worker> pageInfo = new PageInfo<>(workers);
            workerVO.setWorkers(pageInfo.getList());
            workerVO.setCount(workerService.count(property, value));
            workerVO.setCode(0);
            workerVO.setMsg("查询成功!");
        } catch (Exception e) {
            workerVO.setMsg("发生了异常");
            workerVO.setCode(-1);
            workerVO.setCount(0);
            e.printStackTrace();
        } finally {
            return workerVO;
        }
    }

    @RequestMapping(value = "modifyWorkerInfo.do")
    @ResponseBody
    public MessageVO doModifyWorkerInfo(@RequestParam("workerName") String workerName,
                                        @RequestParam("workerTel") String workerTel,
                                        @RequestParam("workerId") String workerId,
                                        @RequestParam("workerDescription") String workerDescription,
                                        @RequestParam("gender") String gender,
                                        @RequestParam("cost") int cost) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = workerService.updateWorker(workerId, workerName, workerTel, workerDescription, gender, cost);
            messageVO.setMsgId(res);
            if (res > 0) {
                messageVO.setMsgContent("更新成功");
            } else if (res == 0) {
                messageVO.setMsgContent("操作成功,但未引起数据变化");
            } else {
                messageVO.setMsgContent("更新失败");
            }
        } catch (Exception e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("发生了异常!");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }

    @RequestMapping(value = "deleteWorker.do")
    @ResponseBody
    public MessageVO doDeleteWorker(@RequestParam("workerId") String workerId) {
        MessageVO messageVO = new MessageVO();
        try {
            int res = workerService.removeWorker(workerId);
            messageVO.setMsgId(res);
            if (res > 0) {
                messageVO.setMsgContent("删除成功!");
            } else if (res == 0) {
                messageVO.setMsgContent("操作成功,但未对数据造成影响!");
            } else {
                messageVO.setMsgContent("删除时发生了错误");
            }
        } catch (Exception e) {
            messageVO.setMsgId(-1);
            messageVO.setMsgContent("发生了异常!");
            e.printStackTrace();
        } finally {
            return messageVO;
        }
    }
}
