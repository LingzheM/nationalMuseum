package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.Exhibition;

import java.util.List;

public interface ExhibitionMapper {
    int insert(Exhibition record);

    int insertSelective(Exhibition record);

    List<Exhibition> selectByFloorAndBuild(String floor, String buildingId);
}