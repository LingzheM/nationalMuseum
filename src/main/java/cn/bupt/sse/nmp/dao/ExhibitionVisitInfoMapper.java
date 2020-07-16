package cn.bupt.sse.nmp.dao;

import cn.bupt.sse.nmp.entity.ExhibitionVisitInfo;

import java.util.List;
import java.util.Map;

public interface ExhibitionVisitInfoMapper {
    int insert(ExhibitionVisitInfo record);

    int insertSelective(ExhibitionVisitInfo record);

    List<Map<String,Integer>> selectTopEidByDay(Integer number);
}
