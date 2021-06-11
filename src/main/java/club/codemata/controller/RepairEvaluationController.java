package club.codemata.controller;

import club.codemata.bo.RepairEvaluationBO;
import club.codemata.service.IRepairEvaluationService;
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
 * @ClassName RepairEvaluationController
 * @Description 物业维修服务评价的控制器
 * @createTime 2021/04/22 22:37:00
 */
@Controller
@RequestMapping(value = "/RepairEvaluation/")
public class RepairEvaluationController {
    private IRepairEvaluationService evaluationService;

    @Autowired
    @Qualifier("RepairEvaluationService")
    public void setEvaluationService(IRepairEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @RequestMapping("addRepairEvaluation.do")
    @ResponseBody
    public HashMap<String,Object> doAddRepairEvaluation(@RequestParam(value = "userId") String userId,
                                                        @RequestParam(value = "workerId") String workerId,
                                                        @RequestParam(value = "evaluationContent") String evaluationContent,
                                                        @RequestParam(value = "evaluationScore") int evaluationScore,
                                                        @RequestParam(value = "repairId") String repairId) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            int res = evaluationService.addRepairEvaluation(userId, workerId, evaluationContent, evaluationScore, repairId);
            if (res > 0) {
                result.put("status", "success");
            } else {
                result.put("status", "error");
            }
        } catch (Exception e) {
            result.put("status", "exception");
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @RequestMapping("getRepairEvaluations.do")
    @ResponseBody
    public HashMap<String, Object> doGetRepairEvaluations(@RequestParam(value = "workerId") String workerId,
                                                          @RequestParam(value = "page") int page,
                                                          @RequestParam(value = "limit") int limit) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            PageHelper.startPage(page, limit);
            List<RepairEvaluationBO> evaluations = evaluationService.getRepairEvaluations(workerId);
            PageInfo<RepairEvaluationBO> pageInfo = new PageInfo<>(evaluations);
            result.put("status", "success");
            result.put("info", pageInfo.getList());
        } catch (Exception e) {
            result.put("status", "exception");
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
