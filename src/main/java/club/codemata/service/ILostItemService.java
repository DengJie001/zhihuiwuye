package club.codemata.service;

import club.codemata.bo.LostItemBO;
import club.codemata.entity.LostItem;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ILostItemService
 * @Description 失物招领信息业务层接口
 * @createTime 2021/04/28 23:26:00
 */
public interface ILostItemService {
    public int addLostItem(String userId, String itemDescription, String itemCategory, String itemPicture) throws Exception;

    public int modifyLostItemStatus(String id) throws Exception;

    public int removeLostItem(String id, String complaintCategory, String itemCategory, String itemDate) throws Exception;

    public List<LostItemBO> getLostItems(String property, String value) throws Exception;

    public int count(String property, String value) throws Exception;

    public LostItemBO lostItem2LostItemBO(LostItem item) throws Exception;
}
