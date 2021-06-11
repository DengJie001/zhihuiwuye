package club.codemata.dao;

import club.codemata.entity.RepairEvaluation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IRepairEvaluationDao
 * @Description 物业维修服务评价数据库操作接口
 * @createTime 2021/04/22 21:59:00
 */
@Repository
public interface IRepairEvaluationDao {
    /**
     * 增加一条服务评价数据
     * @Date 2021/4/22 22:00
     * @param evaluation 新增加的服务评价
     * @return int
     **/
    public int saveRepairEvaluation(RepairEvaluation evaluation) throws Exception;

    /**
     * 查找服务评价数据 将用于前端的数据展示
     * @Date 2021/4/22 22:02
     * @param workerId 维修人员ID
     * @return java.util.List<club.codemata.entity.RepairEvaluation>
     **/
    public List<RepairEvaluation> getEvaluations(@Param("workerId") String workerId) throws Exception;
}
