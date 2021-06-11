package club.codemata.service;

import club.codemata.bo.RepairEvaluationBO;
import club.codemata.entity.RepairEvaluation;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IRepairEvaluationService
 * @Description 物业维修评价业务层接口
 * @createTime 2021/04/22 22:22:00
 */
public interface IRepairEvaluationService {
    public int addRepairEvaluation(String userId, String workerId, String evaluationContent, int evaluationScore, String repairId) throws Exception;

    public List<RepairEvaluationBO> getRepairEvaluations(String workerId) throws Exception;

    public RepairEvaluationBO repairEvaluation2RepairEvaluationBO(RepairEvaluation evaluation) throws Exception;
}
