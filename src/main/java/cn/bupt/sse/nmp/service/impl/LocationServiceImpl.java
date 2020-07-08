package cn.bupt.sse.nmp.service.impl;

import cn.bupt.sse.nmp.dao.ExhibitionVisitInfoMapper;
import cn.bupt.sse.nmp.dao.ExhibitionMapper;
import cn.bupt.sse.nmp.dao.RoleMapper;
import cn.bupt.sse.nmp.entity.Exhibition;
import cn.bupt.sse.nmp.entity.ExhibitionVisitInfo;
import cn.bupt.sse.nmp.service.LocationService;
import cn.bupt.sse.nmp.util.ExhibitionUtil;
import cn.bupt.sse.nmp.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-06 14:06
 **/
@Slf4j
@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ExhibitionMapper exhibitionMapper;
    @Autowired
    private ExhibitionVisitInfoMapper exhibitionVisitInfoMapper;
    @Override
    public String saveToRedis(String body) {
        JSONObject jsonBody = JSONObject.parseObject(body);
        String userId = jsonBody.getString("userid");
        String floor = jsonBody.getJSONObject("location").getString("z");
        String buildingid = jsonBody.getString("buildingid");
        String timestamp = jsonBody.getString("timestamp");
        //得到游客周围5m内最近的展品
        List<Exhibition> exhibitions =  exhibitionMapper.selectByFloorAndBuild(floor,buildingid);
        Exhibition nearbyExhibition = ExhibitionUtil.NearbyExhibition(exhibitions, jsonBody, 5);
        log.info("获取到展品列表");
        //判断当前用户是否第一次定位（redis floor中是否存在这个游客的楼层定位信息）
        Boolean userExist = RedisUtil.exist(userId);
        //判断是否是用户当前的首次定位（判断redis中存储的活跃用户中是否存在当前定位的用户）
        if(!userExist){
            //得到定位用户的身份类别
            Integer roleType = roleMapper.selectTypeByUserId(2);
//            Integer roleType = roleMapper.selectTypeByUserId(Integer.parseInt(userId));
            jsonBody.put("usertype",roleType);
            //到访人数+1
            RedisUtil.hset(RedisUtil.PERSON_NUMBER,""+roleType,1+"");
            //记录定位者的身份类别到redis方便直接获取相关的统计信息
            RedisUtil.hset(RedisUtil.ACTIVE_USER_TYPE,userId,roleType);
            //记录定位这上次请求定位所在的楼层
            RedisUtil.hset(RedisUtil.ACTIVE_USER_FLOOR,userId,floor);
            if(nearbyExhibition != null){
                JSONObject exhibitionJson = new JSONObject();
                Integer exhibitionId = nearbyExhibition.getExhibitionId();
                exhibitionJson.put("eId",exhibitionId);
                exhibitionJson.put("sTime",timestamp);
                RedisUtil.hset(RedisUtil.ACTIVE_USER_EXHIBIT,userId,exhibitionJson.toJSONString());
                jsonBody.put("exhibition",nearbyExhibition);
            }else{
                jsonBody.put("exhibitionId",null);
            }
    }else{
            //如果有当前用户的定位信息
            //判断楼层和判断展品
            String visitInfo = RedisUtil.hget(RedisUtil.ACTIVE_USER_EXHIBIT, userId);
            if(visitInfo == null && nearbyExhibition !=null ){
                JSONObject exhibitionJson = new JSONObject();
                Integer exhibitionId = nearbyExhibition.getExhibitionId();
                exhibitionJson.put("eId",exhibitionId);
                exhibitionJson.put("sTime",timestamp);
                RedisUtil.hset(RedisUtil.ACTIVE_USER_EXHIBIT,userId,exhibitionJson.toJSONString());
                jsonBody.put("exhibitionId",nearbyExhibition);
            }else if(visitInfo != null){
                JSONObject jsonVisit = JSONObject.parseObject(visitInfo);
                Integer eId = jsonVisit.getInteger("eId");
                Timestamp sTime = new Timestamp(Long.parseLong(jsonVisit.getString("sTime")));
                if(nearbyExhibition == null || eId != nearbyExhibition.getExhibitionId()){
                    //游客离开了上一个参观的展品，吧访问时间 游客id等存库
                    ExhibitionVisitInfo exhibitionVisitInfo = new ExhibitionVisitInfo();
                    exhibitionVisitInfo.setStartTime(sTime);
                    exhibitionVisitInfo.setEndTime(new Timestamp(Long.parseLong(timestamp)));
                    exhibitionVisitInfo.setUserId(Integer.parseInt(userId));
                    exhibitionVisitInfo.setExhibitionId(eId);
                    exhibitionVisitInfoMapper.insert(exhibitionVisitInfo);
                    if(nearbyExhibition == null){
                        RedisUtil.hdel(RedisUtil.ACTIVE_USER_EXHIBIT,userId);
                        jsonBody.put("exhibition",null);
                    }else{
                        Integer exhibitionId = nearbyExhibition.getExhibitionId();
                        RedisUtil.hset(RedisUtil.ACTIVE_USER_EXHIBIT,userId,exhibitionId);
                        jsonBody.put("exhibition",nearbyExhibition);
                    }
                }
            }
        }

        RedisUtil.rpush(jsonBody.getString("userid"),jsonBody.toString());
        return jsonBody.toJSONString();
    }
}
