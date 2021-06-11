package club.codemata.service;

import club.codemata.entity.Worker;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IWorkerService
 * @Description 维修人员业务层接口
 * @createTime 2021/04/15 16:36:00
 */
public interface IWorkerService {
    public int saveWorker(String workerTel, String workerName, String workerDescription, String workerAvatar, String gender, int cost) throws Exception;

    public int removeWorker(String workerId) throws Exception;

    public int updateWorker(String workerId, String workerName, String workerTel, String workerDescription, String gender, int cost) throws Exception;

    public boolean applyRepair(String workerId) throws Exception;

    public List<Worker> getWorkers(String property, Object value) throws Exception;

    public int count(String property, Object value) throws Exception;
}
