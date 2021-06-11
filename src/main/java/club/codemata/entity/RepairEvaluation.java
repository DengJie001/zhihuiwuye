package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName RepairEvaluation
 * @Description 服务评价实体类
 * @createTime 2021/04/22 21:56:00
 */
public class RepairEvaluation {
    private String evaluationId;
    private String userId;
    private String workerId;
    private String repairId;
    private String evaluationContent;
    private int evaluationScore;
    private String evaluationDate;

    public RepairEvaluation() {
    }

    public String getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(String evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public void setEvaluationContent(String evaluationContent) {
        this.evaluationContent = evaluationContent;
    }

    public int getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(int evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public String getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    @Override
    public String toString() {
        return "RepairEvaluation{" +
                "evaluationId='" + evaluationId + '\'' +
                ", userId='" + userId + '\'' +
                ", workerId='" + workerId + '\'' +
                ", repairId='" + repairId + '\'' +
                ", evaluationContent='" + evaluationContent + '\'' +
                ", evaluationScore=" + evaluationScore +
                ", evaluationDate='" + evaluationDate + '\'' +
                '}';
    }
}
