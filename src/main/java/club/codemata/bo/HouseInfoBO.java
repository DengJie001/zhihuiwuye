package club.codemata.bo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName HouseInfoBO
 * @Description 房产信息业务层实体类
 * @createTime 2021/05/05 20:42:00
 */
public class HouseInfoBO {
    private int houseId;
    private String areaId;
    private String unitId;
    private String roomId;
    private String userName;
    private String userTel;

    public HouseInfoBO() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    @Override
    public String toString() {
        return "HouseInfoBO{" +
                "houseId=" + houseId +
                ", areaId='" + areaId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", userName='" + userName + '\'' +
                ", userTel='" + userTel + '\'' +
                '}';
    }
}
