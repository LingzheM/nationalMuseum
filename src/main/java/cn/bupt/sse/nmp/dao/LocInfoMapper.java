package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.LocInfo;

import java.util.List;

public interface LocInfoMapper {
    int insert(LocInfo record);

    int insertSelective(LocInfo record);

    Integer selectMaxfreqByUserId(Integer userId);

    List<LocInfo> selectLastTraceByUserId(Integer userId);
}