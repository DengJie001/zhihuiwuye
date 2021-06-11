package club.codemata.bo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName RepairEvaluationBO
 * @Description 维修评价业务层对象
 * @createTime 2021/04/22 22:24:00
 */
public class RepairEvaluationBO {
    private String evaluationId;
    private String userName;
    private String userGender;
    private String workerId;
    private String repairId;
    private String evaluationContent;
    private int evaluationScore;
    private String evaluationDate;

    public RepairEvaluationBO() {
    }

    public String getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(String evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
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
        return "RepairEvaluationBO{" +
                "evaluationId='" + evaluationId + '\'' +
                ", userName='" + userName + '\'' +
                ", userGender='" + userGender + '\'' +
                ", workerId='" + workerId + '\'' +
                ", repairId='" + repairId + '\'' +
                ", evaluationContent='" + evaluationContent + '\'' +
                ", evaluationScore=" + evaluationScore +
                ", evaluationDate='" + evaluationDate + '\'' +
                '}';
    }
}
