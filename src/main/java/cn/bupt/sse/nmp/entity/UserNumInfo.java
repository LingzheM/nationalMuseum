package cn.bupt.sse.nmp.entity;

import java.util.Date;

public class UserNumInfo {
    private Integer userNumId;

    private String buildingId;

    private Integer floor;

    private String type;

    private Date time;

    private Integer number;

    public UserNumInfo(Integer userNumId, String buildingId, Integer floor, String type, Date time, Integer number) {
        this.userNumId = userNumId;
        this.buildingId = buildingId;
        this.floor = floor;
        this.type = type;
        this.time = time;
        this.number = number;
    }

    public UserNumInfo() {
        super();
    }

    public Integer getUserNumId() {
        return userNumId;
    }

    public void setUserNumId(Integer userNumId) {
        this.userNumId = userNumId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId == null ? null : buildingId.trim();
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}