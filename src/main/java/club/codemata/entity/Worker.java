package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName Worker
 * @Description 物业维修人员数据库参照实体类
 * @createTime 2021/04/15 16:22:00
 */
public class Worker {
    private String workerId;
    private String workerTel;
    private String workerName;
    private String workerAvatar;
    private String gender;
    private int times;  // 该员工维修次数
    private float score;  // 评分
    private int wait;   // 等待人数
    private String workerDescription;
    private int cost;

    public Worker() {
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWorkerTel() {
        return workerTel;
    }

    public void setWorkerTel(String workerTel) {
        this.workerTel = workerTel;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerAvatar() {
        return workerAvatar;
    }

    public void setWorkerAvatar(String workerAvatar) {
        this.workerAvatar = workerAvatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public String getWorkerDescription() {
        return workerDescription;
    }

    public void setWorkerDescription(String workerDescription) {
        this.workerDescription = workerDescription;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "workerId='" + workerId + '\'' +
                ", workerTel='" + workerTel + '\'' +
                ", workerName='" + workerName + '\'' +
                ", workerAvatar='" + workerAvatar + '\'' +
                ", times=" + times +
                ", score=" + score +
                ", wait=" + wait +
                ", workerDescription='" + workerDescription + '\'' +
                '}';
    }
}
