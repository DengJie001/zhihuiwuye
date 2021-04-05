package club.codemata.service.impl;

import club.codemata.bo.EquipmentBO;
import club.codemata.dao.IEquipmentDao;
import club.codemata.dao.IManagerDao;
import club.codemata.entity.Equipment;
import club.codemata.entity.Manager;
import club.codemata.service.IEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName EquipmentServiceImpl
 * @Description 设备信息业务层实现类
 * @createTime 2021/03/10 09:04:00
 */
@Service(value = "EquipmentService")
public class EquipmentServiceImpl implements IEquipmentService {
    private IEquipmentDao equipmentDao;
    private IManagerDao managerDao;

    @Autowired
    @Qualifier(value = "IEquipmentDao")
    public void setEquipmentDao(IEquipmentDao equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    @Autowired
    @Qualifier(value = "IManagerDao")
    public void setManagerDao(IManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    /**
     * @author DengJie
     * @description 新增一条设备信息
     * @Date 2021/3/10 9:14
     * @Param [club.codemata.entity.Equipment]
     * @return int
     */
    @Override
    public int saveEquipment(Equipment equipment) {
        // 去除前端传过来的图片路径最前面的+
        String picturesPath = equipment.getEquipmentPicture();
        equipment.setEquipmentPicture(picturesPath.substring(1, picturesPath.length()));
        System.out.println(equipment);
        return equipmentDao.saveEquipment(equipment);
    }

    /**
     * @author DengJie
     * @description 根据id删除对应的设备信息
     * @Date 2021/3/10 9:14
     * @Param [java.lang.String]
     * @return int
     */
    @Override
    public int removeEquipmentById(String equipmentId) {
        return equipmentDao.removeEquipmentById(equipmentId);
    }

    /**
     * @author DengJie
     * @description 修改一条设备信息
     * @Date 2021/3/10 9:13
     * @Param [club.codemata.entity.Equipment]
     * @return int
     */
    @Override
    public int updateEquipment(Equipment equipment) {
        return equipmentDao.updateEquipment(equipment);
    }

    /**
     * @author DengJie
     * @description 通过id查询一条设备信息
     * @Date 2021/3/10 9:13
     * @Param [java.lang.String]
     * @return club.codemata.entity.Equipment
     */
    @Override
    public Equipment getEquipmentById(String equipmentId) {
        return equipmentDao.getEquipmentById(equipmentId);
    }

    /**
     * @author DengJie
     * @description 根据设备名称查询对应设备的信息
     * @Date 2021/3/10 9:13
     * @Param [java.lang.String]
     * @return java.util.List<club.codemata.entity.Equipment>
     */
    @Override
    public List<Equipment> getEquipmentsByName(String equipmentName) {
        return equipmentDao.getEquipmentsByName(equipmentName);
    }

    /**
     * @author DengJie
     * @description 根据品牌查询对应的所有设备的信息
     * @Date 2021/3/10 9:12
     * @Param [java.lang.String]
     * @return java.util.List<club.codemata.entity.Equipment>
     */
    @Override
    public List<Equipment> getEquipmentsByBrand(String equipmentBrand) {
        return equipmentDao.getEquipmentsByBrand(equipmentBrand);
    }

    /**
     * @author DengJie
     * @description 根据管理员id查询该管理员负责的所有设备信息
     * @Date 2021/3/10 9:12
     * @Param [java.lang.String]
     * @return java.util.List<club.codemata.entity.Equipment>
     */
    @Override
    public List<Equipment> getEquipmentsByManager(String managerId) {
        return equipmentDao.getEquipmentsByManager(managerId);
    }

    /**
     * @author DengJie
     * @description 查询所有的记录
     * @Date 2021/3/10 9:11
     * @Param []
     * @return java.util.List<club.codemata.entity.Equipment>
     */
    @Override
    public List<EquipmentBO> getAllEquipments() {
        List<Equipment> equipments = equipmentDao.getAllEquipments();
        List<EquipmentBO> equipmentBOs = new ArrayList<>();
        for (Equipment equipment : equipments) {
            equipmentBOs.add(equipment2EquipmentBO(equipment));
        }
        return equipmentBOs;
    }

    /**
     * @author DengJie
     * @description 根据属性对应关键字对设备信息进行计数
     * @Date 2021/3/17 23:43
     * @Param [java.lang.String, java.lang.String]
     * @return int
     */
    public int count(String property, String keyWords) {
        if (property != null && keyWords != null){
            if (property.equals("managerId")) {
                property = "manager";
            } else if (property.equals("equipmentBrand")) {
                keyWords = "%" + keyWords + "%";
            } else if (property.equals("equipmentName")) {
                keyWords = "%" + keyWords + "%";
            }
        }
        return equipmentDao.count(property, keyWords);
    }

    /**
     * @author DengJie
     * @description 根据属性(设备ID,设备名称,设备品牌,负责人)关键字查找对应的设备信息
     * @Date 2021/3/17 20:56
     * @Param [java.lang.String, java.lang.String]
     * @return java.util.List<club.codemata.bo.EquipmentBO>
     */
    @Override
    public List<EquipmentBO> search(String property, String keyWords) {
        List<EquipmentBO> equipmentBOS = new ArrayList<>();
        // 根据不同属性关键字进行设备信息查询
        switch (property){
            case "equipmentId":
                Equipment equipmentById = equipmentDao.getEquipmentById(keyWords);
                if (equipmentById != null) {
                    equipmentBOS.add(equipment2EquipmentBO(equipmentById));
                }
                break;
            case "equipmentName":
                List<Equipment> equipmentsByName = equipmentDao.getEquipmentsByName("%" + keyWords + "%");
                if (equipmentsByName.size() > 0) {
                    for (Equipment equipment : equipmentsByName) {
                        equipmentBOS.add(equipment2EquipmentBO(equipment));
                    }
                }
                break;
            case "equipmentBrand":
                List<Equipment> equipmentsByBrand = equipmentDao.getEquipmentsByBrand(keyWords);
                if (equipmentsByBrand.size() > 0) {
                    for (Equipment equipment : equipmentsByBrand) {
                        equipmentBOS.add(equipment2EquipmentBO(equipment));
                    }
                }
                break;
            case "managerId":
                List<Equipment> equipmentsByManager = equipmentDao.getEquipmentsByManager(keyWords);
                if (equipmentsByManager.size() > 0) {
                    for (Equipment equipment : equipmentsByManager) {
                        equipmentBOS.add(equipment2EquipmentBO(equipment));
                    }
                }
                break;
        }
        return equipmentBOS;
    }

    /**
     * @author DengJie
     * @description 将Equipment对象转换为EquipmentBO对象
     * @Date 2021/3/17 22:19
     * @Param [club.codemata.entity.Equipment]
     * @return club.codemata.bo.EquipmentBO
     */
    private EquipmentBO equipment2EquipmentBO(Equipment equipment) {
        EquipmentBO equipmentBO = new EquipmentBO();
        Manager manager = managerDao.getManagerById(equipment.getManagerId());
        equipmentBO.setEquipmentId(equipment.getEquipmentId());
        equipmentBO.setEquipmentPrice(equipment.getEquipmentPrice());
        equipmentBO.setEquipmentName(equipment.getEquipmentName());
        equipmentBO.setEquipmentBrand(equipment.getEquipmentBrand());
        equipmentBO.setEquipmentType(equipment.getEquipmentType());
        equipmentBO.setEquipmentQuantity(equipment.getEquipmentQuantity());
        equipmentBO.setEquipmentPicture(equipment.getEquipmentPicture());
        equipmentBO.setManagerId(manager.getManagerId());
        equipmentBO.setManagerTel(manager.getManagerTel());
        equipmentBO.setManagerName(manager.getManagerName());
        equipmentBO.setBuyDate(equipment.getBuyDate());
        return equipmentBO;
    }
}
