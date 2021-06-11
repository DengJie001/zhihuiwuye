package club.codemata.dao;

import club.codemata.entity.LostItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ILostItemDao
 * @Description 失物招领信息数据库操作接口
 * @createTime 2021/04/28 22:44:00
 */
@Repository
public interface ILostItemDao {
    /**
     * 新增一条失物招领信息
     * @Date 2021/4/28 22:45
     * @param item 新增的失物招领信息
     * @return int
     **/
    public int saveLostItem(LostItem item) throws Exception;

    /**
     * 修改失物招领的状态
     * @Date 2021/4/28 23:12
     * @param id 失物招领ID
     * @param itemStatus 修改后的状态
     * @return int
     **/
    public int updateLostItemStatus(@Param("id") String id, @Param("itemStatus") String itemStatus) throws Exception;

    /**
     * 删除一条失物招领信息
     * @Date 2021/4/28 22:46
     * @param id
     * @return int
     **/
    public int removeLostItem(@Param("id") String id) throws Exception;

    /**
     * 根据失物招领信息ID查找一条失物招领信息
     * @Date 2021/4/28 22:47
     * @param id 失物招领的信息ID
     * @return club.codemata.entity.LostItem
     **/
    public LostItem getLostItemById(@Param("id") String id) throws Exception;

    /**
     * 查找所有的失物招领信息
     * @Date 2021/4/28 23:49
     * @param
     * @return java.util.List<club.codemata.entity.LostItem>
     **/
    public List<LostItem> getAllLostItems() throws Exception;

    /**
     * 查找对应用户发布的所有失物招领信息
     * @Date 2021/4/28 22:48
     * @param userId 用户ID
     * @return java.util.List<club.codemata.entity.LostItem>
     **/
    public List<LostItem> getLostItemsByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 根据失物招领信息的类别查找所有对应的失物招领信息
     * @Date 2021/4/28 22:51
     * @param itemCategory 失物招领信息的类别--失物招领，寻物启事
     * @return java.util.List<club.codemata.entity.LostItem>
     **/
    public List<LostItem> getLostItemsByCategory(@Param("itemCategory") String itemCategory) throws Exception;

    /**
     * 根据失物招领的状态查询失物招领信息
     * @Date 2021/4/28 23:00
     * @param itemStatus 失物招领的状态--已解决,未解决
     * @return java.util.List<club.codemata.entity.LostItem>
     **/
    public List<LostItem> getLostItemsByStatus(@Param("itemStatus") String itemStatus) throws Exception;

    /**
     * 根据失物招领物品描述的关键字查找失物招领信息
     * @Date 2021/4/28 23:04
     * @param keyWords 关键字
     * @return java.util.List<club.codemata.entity.LostItem>
     **/
    public List<LostItem> getLostItemsByKeyWords(@Param("keyWords") String keyWords) throws Exception;

    public int count(@Param("property") String property, @Param("value") String value) throws Exception;
}
