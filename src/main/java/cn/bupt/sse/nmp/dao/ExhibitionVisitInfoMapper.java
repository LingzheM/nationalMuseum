package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.ExhibitionVisitInfo;

public interface ExhibitionVisitInfoMapper {
    int insert(ExhibitionVisitInfo record);

    int insertSelective(ExhibitionVisitInfo record);
}