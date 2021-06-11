package club.codemata.service.impl;

import club.codemata.bo.LostItemBO;
import club.codemata.dao.ILostItemComplaintDao;
import club.codemata.dao.ILostItemDao;
import club.codemata.dao.IUserDao;
import club.codemata.entity.LostItem;
import club.codemata.entity.User;
import club.codemata.service.ILostItemService;
import club.codemata.utils.SMSUtils;
import club.codemata.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LostItemServiceImpl
 * @Description 失物招领西悉尼业务层逻辑
 * @createTime 2021/04/28 23:37:00
 */
@Service(value = "LostItemService")
public class LostItemServiceImpl implements ILostItemService {
    private IUserDao userDao;
    private ILostItemDao lostItemDao;
    private ILostItemComplaintDao complaintDao;

    @Autowired
    @Qualifier(value = "IUserDao")
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    @Qualifier(value = "ILostItemDao")
    public void setLostItemDao(ILostItemDao lostItemDao) {
        this.lostItemDao = lostItemDao;
    }

    @Autowired
    @Qualifier(value = "ILostItemComplaintDao")
    public void setComplaintDao(ILostItemComplaintDao complaintDao) {
        this.complaintDao = complaintDao;
    }

    /**
     * 新增一条失物招领信息
     * @Date 2021/4/28 23:38
     * @param userId 发布该条失物招领的用户ID
     * @param itemDescription 失物招领的详细内容
     * @param itemCategory 失物招领/寻物启事
     * @param itemPicture 物品的图片
     * @return int
     **/
    @Override
    public int addLostItem(String userId, String itemDescription, String itemCategory, String itemPicture) throws Exception {
        // 封装失物招领的相关信息
        LostItem item = new LostItem();
        item.setId(UUIDUtils.getUUID());
        item.setUserId(userId);
        item.setItemDescription(itemDescription);
        item.setItemPicture(itemPicture.substring(1));
        item.setItemCategory(itemCategory);
        return lostItemDao.saveLostItem(item);
    }

    /**
     * 将失物招领的状态修改未已解决
     * @Date 2021/4/28 23:46
     * @param id 要修改的失物招领的ID
     * @return int
     **/
    @Override
    public int modifyLostItemStatus(String id) throws Exception {
        return lostItemDao.updateLostItemStatus(id, "已解决");
    }

    /**
     * 删除一条失物招领信息
     * @Date 2021/4/28 23:46
     * @param id 要删除的失物招领的ID
     * @return int
     **/
    @Override
    public int removeLostItem(String id, String complaintCategory, String itemCategory, String itemDate) throws Exception {
        if (complaintCategory != null && itemCategory != null && itemDate != null) {
            LostItem lostItem = lostItemDao.getLostItemById(id);
            User user = userDao.getUserById(lostItem.getUserId());
            SMSUtils.sendDeleteLostItemInfo(user.getUserTel(), itemDate.split("-")[0], itemDate.split("-")[1], itemDate.split("-")[2], itemCategory, complaintCategory);
        }
        return lostItemDao.removeLostItem(id);
    }

    /**
     * 根据属性和他的值查找失物招领信息
     * @Date 2021/4/28 23:47
     * @param property 属性--用户ID,类别,状态,关键字
     * @param value 属性对应值
     * @return java.util.List<club.codemata.bo.LostItemBO>
     **/
    @Override
    public List<LostItemBO> getLostItems(String property, String value) throws Exception {
        List<LostItemBO> bos = new ArrayList<>();
        if ("用户ID".equals(property)) {
            List<LostItem> items = lostItemDao.getLostItemsByUserId(value);
            if (items.size() > 0) {
                for (LostItem item : items) {
                    bos.add(lostItem2LostItemBO(item));
                }
            }
        } else if ("类别".equals(property)) {
            List<LostItem> items = lostItemDao.getLostItemsByCategory(value);
            if (items.size() > 0) {
                for (LostItem item : items) {
                    bos.add(lostItem2LostItemBO(item));
                }
            }
        } else if ("状态".equals(property)) {
            List<LostItem> items = lostItemDao.getLostItemsByStatus(value);
            if (items.size() > 0) {
                for (LostItem item : items) {
                    bos.add(lostItem2LostItemBO(item));
                }
            }
        } else if ("关键字".equals(property)) {
            value = "%" + value + "%";
            List<LostItem> items = lostItemDao.getLostItemsByKeyWords(value);
            if (items.size() > 0) {
                for (LostItem item : items) {
                    bos.add(lostItem2LostItemBO(item));
                }
            }
        } else {
            List<LostItem> items = lostItemDao.getAllLostItems();
            if (items.size() > 0) {
                for (LostItem item : items) {
                    bos.add(lostItem2LostItemBO(item));
                }
            }
        }
        return bos;
    }

    @Override
    public int count(String property, String value) throws Exception {
        if ("关键字".equals(property)) {
            value = "%" + value + "%";
        }
        return lostItemDao.count(property, value);
    }

    /**
     * 将LostItem的对象封装为前端展示所需要的LostItemBO的对象
     * @Date 2021/4/28 23:52
     * @param item 需要被封装的LostItem对象
     * @return club.codemata.bo.LostItemBO
     **/
    @Override
    public LostItemBO lostItem2LostItemBO(LostItem item) throws Exception {
        LostItemBO bo = new LostItemBO();
        // 查找发布人的相关信息
        User user = userDao.getUserById(item.getUserId());
        bo.setId(item.getId());
        bo.setUserId(item.getUserId());
        bo.setUserName(user.getUserName());
        bo.setUserTel(user.getUserTel());
        bo.setItemDescription(item.getItemDescription());
        bo.setItemPicture(item.getItemPicture());
        bo.setItemCategory(item.getItemCategory());
        bo.setItemStatus(item.getItemStatus());
        bo.setItemDate(item.getItemDate());
        return bo;
    }
}
