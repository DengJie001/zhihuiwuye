package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceInfo
 * @Description 活动场地信息实体类
 * @createTime 2021/03/27 18:22:00
 */
public class PlaceInfo {
    private int placeId;
    private String managerId;
    private int placeArea;
    private int placePrice;
    private String placeDescription;
    private String placePicture;
    private String placeStatus;

    public PlaceInfo() {
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public int getPlaceArea() {
        return placeArea;
    }

    public void setPlaceArea(int placeArea) {
        this.placeArea = placeArea;
    }

    public int getPlacePrice() {
        return placePrice;
    }

    public void setPlacePrice(int placePrice) {
        this.placePrice = placePrice;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlacePicture() {
        return placePicture;
    }

    public void setPlacePicture(String placePicture) {
        this.placePicture = placePicture;
    }

    public String getPlaceStatus() {
        return placeStatus;
    }

    public void setPlaceStatus(String placeStatus) {
        this.placeStatus = placeStatus;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "placeId=" + placeId +
                ", managerId='" + managerId + '\'' +
                ", placeArea=" + placeArea +
                ", placePrice=" + placePrice +
                ", placeDescription='" + placeDescription + '\'' +
                ", placePicture='" + placePicture + '\'' +
                ", placeStatus='" + placeStatus + '\'' +
                '}';
    }
}
