package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName HouseInfo
 * @Description 房产信息实体类
 * @createTime 2021/05/05 20:19:00
 */
public class HouseInfo {
    private int houseId;
    private String areaId;
    private String unitId;
    private String roomId;

    public HouseInfo() {
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "HouseInfo{" +
                "houseId=" + houseId +
                ", areaId='" + areaId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
