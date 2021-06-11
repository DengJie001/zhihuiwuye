package club.codemata.bo;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceInfoBO
 * @Description 场地信息的业务层对象
 * @createTime 2021/04/06 00:14:00
 */
public class PlaceInfoBO {
    private int placeId;
    private String managerId;
    private String managerName;
    private String managerTel;
    private int placeArea;
    private int placePrice;
    private String placeDescription;
    private String placePicture;
    private String placeStatus;

    public PlaceInfoBO() {
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

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerTel() {
        return managerTel;
    }

    public void setManagerTel(String managerTel) {
        this.managerTel = managerTel;
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
        return "PlaceInfoBO{" +
                "placeId=" + placeId +
                ", managerId='" + managerId + '\'' +
                ", managerName='" + managerName + '\'' +
                ", managerTel='" + managerTel + '\'' +
                ", placeArea=" + placeArea +
                ", placePrice=" + placePrice +
                ", placeDescription='" + placeDescription + '\'' +
                ", placePicture='" + placePicture + '\'' +
                ", placeStatus='" + placeStatus + '\'' +
                '}';
    }
}
