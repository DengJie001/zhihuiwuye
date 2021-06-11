package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceApplicationResult
 * @Description 场地申请处理结果实体类
 * @createTime 2021/04/12 00:05:00
 */
public class PlaceApplicationResult {
    private String resultId;
    private String result;
    private String resultDescription;
    private String managerId;
    private String resultDate;

    public PlaceApplicationResult() {
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    @Override
    public String toString() {
        return "PlaceApplicationResult{" +
                "resultId='" + resultId + '\'' +
                ", result='" + result + '\'' +
                ", resultDescription='" + resultDescription + '\'' +
                ", managerId='" + managerId + '\'' +
                ", resultDate='" + resultDate + '\'' +
                '}';
    }
}
