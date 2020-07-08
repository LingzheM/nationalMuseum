package cn.bupt.sse.nmp.entity;

public class Exhibition {
    private Integer exhibitionId;

    private String exhibitionName;

    private Double positionX;

    private Double positionY;

    private Integer floor;

    private String type;

    private String buildingId;

    private String description;

    private String picture;

    public Exhibition(Integer exhibitionId, String exhibitionName, Double positionX, Double positionY, Integer floor, String type, String buildingId, String description, String picture) {
        this.exhibitionId = exhibitionId;
        this.exhibitionName = exhibitionName;
        this.positionX = positionX;
        this.positionY = positionY;
        this.floor = floor;
        this.type = type;
        this.buildingId = buildingId;
        this.description = description;
        this.picture = picture;
    }

    public Exhibition() {
        super();
    }

    public Integer getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Integer exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public String getExhibitionName() {
        return exhibitionName;
    }

    public void setExhibitionName(String exhibitionName) {
        this.exhibitionName = exhibitionName == null ? null : exhibitionName.trim();
    }

    public Double getPositionX() {
        return positionX;
    }

    public void setPositionX(Double positionX) {
        this.positionX = positionX;
    }

    public Double getPositionY() {
        return positionY;
    }

    public void setPositionY(Double positionY) {
        this.positionY = positionY;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId == null ? null : buildingId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }
}