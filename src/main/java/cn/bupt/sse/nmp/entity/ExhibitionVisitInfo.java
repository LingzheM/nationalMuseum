package cn.bupt.sse.nmp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("展品被参观记录模型")
public class ExhibitionVisitInfo {

    @ApiModelProperty("展品Id")
    private Integer exhibitionId;

    @ApiModelProperty("参观开始时间")
    private Date startTime;

    @ApiModelProperty("参观结束时间")
    private Date endTime;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("参观记录Id")
    private Integer visitId;

    public ExhibitionVisitInfo(Integer exhibitionId, Date startTime, Date endTime, Integer userId, Integer visitId) {
        this.exhibitionId = exhibitionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.visitId = visitId;
    }
    public ExhibitionVisitInfo(Integer exhibitionId, Date startTime, Date endTime, Integer userId) {
        this.exhibitionId = exhibitionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
    }

    public ExhibitionVisitInfo() {
        super();
    }

    public Integer getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Integer exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }
}