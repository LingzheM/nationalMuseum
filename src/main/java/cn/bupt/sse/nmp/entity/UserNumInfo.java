package cn.bupt.sse.nmp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
@ApiModel("用户数量统计模型")
public class UserNumInfo {
    @ApiModelProperty("用户数量Id")
    private Integer userNumId;

    @ApiModelProperty("楼宇Id")
    private String buildingId;

    @ApiModelProperty("楼层")
    private Integer floor;

    @ApiModelProperty("用户类别")
    private String type;

    @ApiModelProperty("统计时间")
    private Date time;

    @ApiModelProperty("新增用户数量")
    private Integer number;

    public UserNumInfo(Integer userNumId, String buildingId, Integer floor, String type, Date time, Integer number) {
        this.userNumId = userNumId;
        this.buildingId = buildingId;
        this.floor = floor;
        this.type = type;
        this.time = time;
        this.number = number;
    }
    public UserNumInfo(String buildingId, Integer floor, String type, Date time, Integer number) {
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