package club.codemata.dao;

import club.codemata.entity.LostItemComplaint;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ILostItemComplaintDao
 * @Description 失物招领举报数据库操作接口
 * @createTime 2021/05/07 17:06:00
 */
@Repository
public interface ILostItemComplaintDao {
    public int saveComplaint(LostItemComplaint complaint) throws Exception;

    public List<LostItemComplaint> getAllLostItemComplaints() throws Exception;

    public int count() throws Exception;
}
