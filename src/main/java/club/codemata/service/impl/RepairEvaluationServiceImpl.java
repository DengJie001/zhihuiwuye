package club.codemata.service.impl;

import club.codemata.bo.RepairEvaluationBO;
import club.codemata.dao.IPropertyRepairInfoDao;
import club.codemata.dao.IRepairEvaluationDao;
import club.codemata.dao.IUserDao;
import club.codemata.dao.IWorkerDao;
import club.codemata.entity.RepairEvaluation;
import club.codemata.entity.User;
import club.codemata.entity.Worker;
import club.codemata.service.IRepairEvaluationService;
import club.codemata.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName RepairEvaluationServiceImpl
 * @Description 维修服务评价业务层逻辑
 * @createTime 2021/04/22 22:27:00
 */
@Service(value = "RepairEvaluationService")
@Transactional(rollbackFor = Exception.class)
public class RepairEvaluationServiceImpl implements IRepairEvaluationService {
    private IRepairEvaluationDao evaluationDao;
    private IUserDao userDao;
    private IWorkerDao workerDao;
    private IPropertyRepairInfoDao infoDao;

    @Autowired
    @Qualifier("IRepairEvaluationDao")
    public void setEvaluationDao(IRepairEvaluationDao evaluationDao) {
        this.evaluationDao = evaluationDao;
    }

    @Autowired
    @Qualifier("IUserDao")
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    @Qualifier("IWorkerDao")
    public void setWorkerDao(IWorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    @Autowired
    @Qualifier("IPropertyRepairInfoDao")
    public void setInfoDao(IPropertyRepairInfoDao infoDao) {
        this.infoDao = infoDao;
    }

    /**
     * 新增加一条物业维修评价
     * @Date 2021/4/22 22:34
     * @param userId 用户ID
     * @param workerId 被评价的物业维修人员的ID
     * @param evaluationContent 评价内容
     * @param evaluationScore 评分
     * @return int
     **/
    @Override
    public synchronized int addRepairEvaluation(String userId, String workerId, String evaluationContent, int evaluationScore, String repairId) throws Exception {
        RepairEvaluation evaluation = new RepairEvaluation();
        evaluation.setEvaluationId(UUIDUtils.getUUID());
        evaluation.setUserId(userId);
        evaluation.setWorkerId(workerId);
        evaluation.setEvaluationContent(evaluationContent);
        evaluation.setEvaluationScore(evaluationScore);
        evaluation.setRepairId(repairId);
        int res = evaluationDao.saveRepairEvaluation(evaluation);
        // 如果评价提交成功 那么重新计算该员工的评分 将该员工的等待人数-1 并且将该条记录的维修工单状态更新为已完成
        if (res > 0) {
            workerDao.updateWorkerTimes(workerId, 1);
            Worker worker = workerDao.getWorkerByWorkerId(workerId);
            int score = 100;
            // 查询该用户的所有被评价记录 计算总得分 然后计算平均分
            List<RepairEvaluation> evaluations = evaluationDao.getEvaluations(workerId);
            if (evaluations.size() > 0) {
                for (RepairEvaluation tempEvaluation : evaluations) {
                    score += tempEvaluation.getEvaluationScore();
                }
            }
            workerDao.updateWorkerScore(workerId, (int) (score / (worker.getTimes() + 1)));
            workerDao.updateWorkerWait(workerId, -1);
            // 修改工单状态
            infoDao.updatePropertyRepairInfoOrderStatus(repairId, "已完成");
        }
        return res;
    }

    /**
     * 获取某个物业维修人员的所有评价
     * @Date 2021/4/22 22:35
     * @param workerId 物业维修人员ID
     * @return java.util.List<club.codemata.bo.RepairEvaluationBO>
     **/
    @Override
    public List<RepairEvaluationBO> getRepairEvaluations(String workerId) throws Exception {
        List<RepairEvaluationBO> repairEvaluationBOS = new ArrayList<>();
        List<RepairEvaluation> evaluations = evaluationDao.getEvaluations(workerId);
        if (evaluations.size() > 0) {
            for (RepairEvaluation evaluation : evaluations) {
                repairEvaluationBOS.add(repairEvaluation2RepairEvaluationBO(evaluation));
            }
        }
        return repairEvaluationBOS;
    }

    /**
     * 将RepairEvaluation的对象封装为RepairEvaluationBO的对象 用于前端展示
     * @Date 2021/4/22 22:31
     * @param evaluation 要封装的RepairEvaluation对象
     * @return club.codemata.bo.RepairEvaluationBO
     **/
    @Override
    public RepairEvaluationBO repairEvaluation2RepairEvaluationBO(RepairEvaluation evaluation) throws Exception {
        RepairEvaluationBO repairEvaluationBO = new RepairEvaluationBO();
        User user = userDao.getUserById(evaluation.getUserId());
        repairEvaluationBO.setEvaluationId(evaluation.getEvaluationId());
        repairEvaluationBO.setUserName(user.getUserName());
        repairEvaluationBO.setUserGender(user.getGender());
        repairEvaluationBO.setWorkerId(evaluation.getWorkerId());
        repairEvaluationBO.setRepairId(evaluation.getRepairId());
        repairEvaluationBO.setEvaluationContent(evaluation.getEvaluationContent());
        repairEvaluationBO.setEvaluationScore(evaluation.getEvaluationScore());
        repairEvaluationBO.setEvaluationDate(evaluation.getEvaluationDate());
        return repairEvaluationBO;
    }
}
