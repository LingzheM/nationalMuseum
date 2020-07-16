package cn.bupt.sse.nmp.service;

import cn.bupt.sse.nmp.entity.LocInfo;

import java.util.List;
import java.util.Map;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-10 15:40
 **/
public interface MuseumStatisticsService {
    Map<String, Integer> getPersonNum(String buildingId, Integer floor);

    List<Map<String,Integer>> topExhibition(Integer number);

    List<String> activeUser(Integer floor);

    List<String> userTrace(Integer userId);
}
