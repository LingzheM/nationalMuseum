package cn.bupt.sse.nmp.service.impl;

import cn.bupt.sse.nmp.dao.ExhibitionVisitInfoMapper;
import cn.bupt.sse.nmp.dao.LocInfoMapper;
import cn.bupt.sse.nmp.entity.LocInfo;
import cn.bupt.sse.nmp.service.MuseumStatisticsService;
import cn.bupt.sse.nmp.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-10 15:51
 **/
@Service
public class MuseumStatisticsServiceImpl implements MuseumStatisticsService {
    @Autowired
    private ExhibitionVisitInfoMapper exhibitionVisitInfoMapper;
    @Autowired
    private LocInfoMapper locInfoMapper;
    @Override
    public Map<String, Integer> getPersonNum(String buildingId, Integer floor) {
        //获取对应楼层的总游客数量 和各类人的数量
        int PersonAllNum = 0;
        Map<String, Integer> map = new HashMap<>();
        List<String> keys = RedisUtil.hkeys(RedisUtil.ACTIVE_USER_FLOOR);
        for (String key : keys) {
            if(Integer.parseInt(RedisUtil.hget(RedisUtil.ACTIVE_USER_FLOOR,key)) == floor){
                String type = RedisUtil.hget(RedisUtil.ACTIVE_USER_TYPE, key);
                if(type != null){
                    map.put(type,map.getOrDefault(type,0)+1);
                    PersonAllNum++;
                }
            }
        }
        map.put("buildPersonNum",PersonAllNum);
        return map;
    }

    @Override
    public List<Map<String,Integer>> topExhibition(Integer number) {
        List<Map<String,Integer>> list = exhibitionVisitInfoMapper.selectTopEidByDay(number);
        return list;
    }

    /**
     *从Redis中首先获取RedisUtil.ACTIVE_USER_FLOOR活跃用户的楼层信息，之后返回对应楼层的用户
     */
    @Override
    public List<String> activeUser(Integer floor) {
        List<String> userIds = RedisUtil.hkeys(RedisUtil.ACTIVE_USER_FLOOR);
        List<String> result = new ArrayList<>();
        for (String userId : userIds) {
            if(Integer.parseInt(RedisUtil.hget(RedisUtil.ACTIVE_USER_FLOOR,userId)) == floor ){
                result.add(userId);
            }
        }
        return result;
    }

    @Override
    public List<String> userTrace(Integer userId) {
        //活跃用户列表可能已经刷新，导致此用户已经离开
        List<String> trace = new ArrayList<>();
        //用户没有离开，Redis中存储着用户的locInfo.toJsonString
        trace = RedisUtil.lrange(userId + "", 0L, RedisUtil.llen(userId + ""));
        //如果用户离开了需要从数据库中查
        if(trace == null){
            List<LocInfo> locInfos = locInfoMapper.selectLastTraceByUserId(userId);
            for (LocInfo locInfo : locInfos) {
                trace.add(JSONObject.toJSONString(locInfo));
            }
        }

        return trace;
    }
}
