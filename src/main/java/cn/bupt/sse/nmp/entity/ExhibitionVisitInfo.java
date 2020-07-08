package cn.bupt.sse.nmp.entity;

import java.util.Date;

public class ExhibitionVisitInfo {
    private Integer exhibitionId;

    private Date startTime;

    private Date endTime;

    private Integer userId;

    private Integer visitId;

    public ExhibitionVisitInfo(Integer exhibitionId, Date startTime, Date endTime, Integer userId, Integer visitId) {
        this.exhibitionId = exhibitionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.visitId = visitId;
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