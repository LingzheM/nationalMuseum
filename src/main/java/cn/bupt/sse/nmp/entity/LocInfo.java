package cn.bupt.sse.nmp.entity;

import java.util.Date;

public class LocInfo {
    private Integer locId;

    private Integer userId;

    private Date locationTime;

    private Double locationX;

    private Double locationY;

    private Double gpsX;

    private Double gpsY;

    private Integer floor;

    private Integer frequency;

    private String buildingId;

    private Integer exhibitionId;

    private Integer type;

    public LocInfo(Integer locId, Integer userId, Date locationTime, Double locationX, Double locationY, Double gpsX, Double gpsY, Integer floor, Integer frequency, String buildingId, Integer exhibitionId, Integer type) {
        this.locId = locId;
        this.userId = userId;
        this.locationTime = locationTime;
        this.locationX = locationX;
        this.locationY = locationY;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.floor = floor;
        this.frequency = frequency;
        this.buildingId = buildingId;
        this.exhibitionId = exhibitionId;
        this.type = type;
    }

    public LocInfo() {
        super();
    }

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
        this.locId = locId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(Date locationTime) {
        this.locationTime = locationTime;
    }

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    public Double getGpsX() {
        return gpsX;
    }

    public void setGpsX(Double gpsX) {
        this.gpsX = gpsX;
    }

    public Double getGpsY() {
        return gpsY;
    }

    public void setGpsY(Double gpsY) {
        this.gpsY = gpsY;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId == null ? null : buildingId.trim();
    }

    public Integer getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Integer exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}