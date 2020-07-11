package cn.bupt.sse.nmp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("定位信息模型")
public class LocInfo {
    @ApiModelProperty("定位信息id")
    private Integer locId;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("定位时间")
    private Date locationTime;

    @ApiModelProperty("定位X坐标")
    private Double locationX;

    @ApiModelProperty("定位Y坐标")
    private Double locationY;

    @ApiModelProperty("gps经度")
    private Double gpsX;

    @ApiModelProperty("gps纬度")
    private Double gpsY;

    @ApiModelProperty("楼层")
    private Integer floor;

    @ApiModelProperty("此次定位的频次")
    private Integer frequency;

    @ApiModelProperty("楼宇Id")
    private String buildingId;

    @ApiModelProperty("展品Id")
    private Integer exhibitionId;

    @ApiModelProperty("用户的角色类别")
    private String type;

    public LocInfo(Integer locId, Integer userId, Date locationTime, Double locationX, Double locationY, Double gpsX, Double gpsY, Integer floor, Integer frequency, String buildingId, Integer exhibitionId, String type) {
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

    public LocInfo(Integer userId, Date locationTime, Double locationX, Double locationY, Double gpsX, Double gpsY, Integer floor, String buildingId, Integer exhibitionId, String type) {
        this.userId = userId;
        this.locationTime = locationTime;
        this.locationX = locationX;
        this.locationY = locationY;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.floor = floor;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}