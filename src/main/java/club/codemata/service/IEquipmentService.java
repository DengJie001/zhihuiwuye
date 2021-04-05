package club.codemata.service;

import club.codemata.bo.EquipmentBO;
import club.codemata.entity.Equipment;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName IEquipmentService
 * @Description 设备信息业务层接口
 * @createTime 2021/03/10 09:00:00
 */
public interface IEquipmentService {
    public int saveEquipment(Equipment equipment);

    public int removeEquipmentById(String equipmentId);

    public int updateEquipment(Equipment equipment);

    public Equipment getEquipmentById(String equipmentId);

    public List<Equipment> getEquipmentsByName(String equipmentName);

    public List<Equipment> getEquipmentsByBrand(String equipmentBrand);

    public List<Equipment> getEquipmentsByManager(String managerId);

    public List<EquipmentBO> getAllEquipments();

    public int count(String property, String keyWords);

    public List<EquipmentBO> search(String property, String keyWords);
}
