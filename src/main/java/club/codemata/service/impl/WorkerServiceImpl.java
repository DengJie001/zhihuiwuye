package club.codemata.service.impl;

import club.codemata.dao.IWorkerDao;
import club.codemata.entity.Worker;
import club.codemata.service.IWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName WorkerServiceImpl
 * @Description 维修人员业务层逻辑
 * @createTime 2021/04/15 16:39:00
 */
@Service(value = "WorkerService")
@Transactional(rollbackFor = Exception.class)
public class WorkerServiceImpl implements IWorkerService {
    private IWorkerDao workerDao;

    @Autowired
    @Qualifier("IWorkerDao")
    public void setWorkerDao(IWorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    /**
     * 增加一个维修人员的信息
     * @Date 2021/4/15 16:39
     * @param workerTel 维修人员联系方式
     * @param workerName 维修人员姓名
     * @param workerDescription 维修人员简要介绍
     * @param workerAvatar 维修人员的头像
     * @return int
     **/
    @Override
    public int saveWorker(String workerTel, String workerName, String workerDescription, String workerAvatar, String gender, int cost) throws Exception {
        Worker worker = new Worker();
        // 拼接维修人员ID位:w_workerTel
        worker.setWorkerId("w_" + workerTel);
        worker.setWorkerName(workerName);
        worker.setWorkerTel(workerTel);
        worker.setWorkerAvatar(workerAvatar);
        worker.setGender(gender);
        worker.setWorkerDescription(workerDescription);
        worker.setScore(100);
        worker.setTimes(0);
        worker.setWait(0);
        worker.setCost(cost);
        System.out.println("workerId:" + worker.getWorkerId());
        int res = workerDao.saveWorker(worker);
        return res;
    }

    /**
     * 删除一条维修人员信息
     * @Date  22:40
     * @param workerId 被删除员工的ID
     * @return int
     **/
    @Override
    public int removeWorker(String workerId) throws Exception {
        return workerDao.removeWorker(workerId);
    }

    /**
     * 更改一条员工基本信息(姓名,电话,介绍)
     * @Date 2021/4/15 22:41
     * @param workerId 员工ID
     * @param workerName 修改后的姓名
     * @param workerTel 修改后的电话
     * @param workerDescription 修改后的介绍
     * @return int
     **/
    @Override
    public int updateWorker(String workerId, String workerName, String workerTel, String workerDescription, String gender, int cost) throws Exception {
        // 将参数封装为一个Worker对象
        Worker worker = new Worker();
        worker.setWorkerId(workerId);
        worker.setWorkerName(workerName);
        worker.setWorkerTel(workerTel);
        worker.setWorkerDescription(workerDescription);
        worker.setGender(gender);
        worker.setCost(cost);
        return workerDao.updateWorker(worker);
    }

    /**
     * 申请进行物业维修<br>
     * 当用户提出申请时 需要将对应员工的服务次数和等待人数同时加1
     * @Date 2021/4/15 22:51
     * @param workerId 员工的ID
     * @return boolean
     **/
    @Override
    public boolean applyRepair(String workerId) throws Exception {
        int updateTimes = workerDao.updateWorkerTimes(workerId, 1);
        int updateWait = workerDao.updateWorkerWait(workerId, 1);
        if (updateTimes > 0 && updateWait > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Worker> getWorkers(String property, Object value) throws Exception {
        List<Worker> workerList = new ArrayList<>();
        if ("维修人员ID".equals(property)) {
            Worker worker = workerDao.getWorkerByWorkerId(value.toString());
            if (worker != null) {
                workerList.add(worker);
            }
        } else if ("评分".equals(property)) {
            List<Worker> workers = workerDao.getWorkersByScore(new Float(value.toString()));
            if (workers.size() > 0) {
                for (Worker worker : workers) {
                    workerList.add(worker);
                }
            }
        } else if ("排队人数".equals(property)) {
            List<Worker> workers = workerDao.getWorkersByWait(new Integer(value.toString()));
            if (workers.size() > 0) {
                for (Worker worker : workers) {
                    workerList.add(worker);
                }
            }
        } else if ("服务次数".equals(property)) {
            List<Worker> workers = workerDao.getWorkersByTimes(new Integer(value.toString()));
            if (workers.size() > 0) {
                for (Worker worker : workers) {
                    workerList.add(worker);
                }
            }
        } else if ("技能".equals(property)) {
            String keyWords = "%" + value.toString() + "%";
            List<Worker> workers = workerDao.getWorkersByDescription(keyWords);
            if (workers.size() > 0) {
                for (Worker worker : workers) {
                    workerList.add(worker);
                }
            }
        } else {
            List<Worker> allWorkers = workerDao.getAllWorkers();
            if (allWorkers.size() > 0) {
                for (Worker worker : allWorkers) {
                    workerList.add(worker);
                }
            }
        }
        return workerList;
    }

    @Override
    public int count(String property, Object value) throws Exception {
        if ("评分".equals(property)) {
            return workerDao.count(property, new Float(value.toString()));
        } else if ("技能".equals(property)) {
            String keyWords = "%" + value.toString() + "%";
            return workerDao.count(property, keyWords);
        } else {
            return workerDao.count(property, value);
        }
    }
}
