package cn.bupt.sse.nmp.service.impl;

import cn.bupt.sse.nmp.dao.*;
import cn.bupt.sse.nmp.entity.Exhibition;
import cn.bupt.sse.nmp.entity.ExhibitionVisitInfo;
import cn.bupt.sse.nmp.entity.LocInfo;
import cn.bupt.sse.nmp.entity.UserNumInfo;
import cn.bupt.sse.nmp.service.LocationService;
import cn.bupt.sse.nmp.util.BeanUtil;
import cn.bupt.sse.nmp.util.ExhibitionUtil;
import cn.bupt.sse.nmp.util.HttpUtils;
import cn.bupt.sse.nmp.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    @Autowired
    private  UserNumInfoMapper userNumInfoMapper;
    @Autowired
    private LocInfoMapper locInfoMapper;
    @Override
    public String saveToRedis(String body) {
        JSONObject jsonBody = JSONObject.parseObject(body);
        String userId = jsonBody.getString("userid");
        String floor = jsonBody.getJSONObject("location").getString("z");
        String buildingid = jsonBody.getString("buildingid");
        String timestamp = jsonBody.getString("timestamp");
        //判断当前用户是否第一次定位（redis floor中是否存在这个游客的楼层定位信息）
        if(!RedisUtil.hexists(RedisUtil.ACTIVE_USER_FLOOR,userId)){
            //记录定位这上次请求定位所在的楼层
            RedisUtil.hset(RedisUtil.ACTIVE_USER_FLOOR,userId,floor);
            //得到定位用户的身份类别
            String roleType = roleMapper.selectTypeByUserId(2);
//          Integer roleType = roleMapper.selectTypeByUserId(Integer.parseInt(userId));
            //记录定位者的身份类别到redis方便直接获取相关的统计信息
            RedisUtil.hset(RedisUtil.ACTIVE_USER_TYPE,userId,roleType);
            jsonBody.put("usertype",roleType);
            //对应楼层的角色类别到访人数+1
            if(RedisUtil.hget(RedisUtil.PERSON_NUMBER,buildingid+"-"+floor) == null){
                JSONObject pNumJson = new JSONObject();
                pNumJson.put(roleType,1);
                RedisUtil.hset(RedisUtil.PERSON_NUMBER,buildingid+"-"+floor,pNumJson.toJSONString());
            }else{
                JSONObject pNumJson = JSONObject.parseObject(RedisUtil.hget(RedisUtil.PERSON_NUMBER, buildingid +"-"+ floor));
                pNumJson.put(roleType,(Integer)pNumJson.getOrDefault(roleType,0)+1);
                RedisUtil.hset(RedisUtil.PERSON_NUMBER,buildingid+"-"+floor,pNumJson.toJSONString());
            }
        }else{
            //如果有当前用户的定位信息，判断是否需要更新楼层信息
            String type = RedisUtil.hget(RedisUtil.ACTIVE_USER_TYPE, userId);
            jsonBody.put("usertype",type);
            if(RedisUtil.hget(RedisUtil.ACTIVE_USER_FLOOR,userId) != floor){
                RedisUtil.hset(RedisUtil.ACTIVE_USER_FLOOR,userId,floor);
            }
        }
        LocInfo locInfo = BeanUtil.locJsonToLocInfo(jsonBody);
        RedisUtil.rpush(userId,JSONObject.toJSONString(locInfo));
        return jsonBody.toJSONString();
    }

    @Override
    public String findExhibition(JSONObject jsonBody) {
        String userId = jsonBody.getString("userid");
        String floor = jsonBody.getJSONObject("location").getString("z");
        String buildingid = jsonBody.getString("buildingid");
        Date timestamp = jsonBody.getDate("timestamp");
        //得到游客周围5m内最近的展品
        List<Exhibition> exhibitions =  exhibitionMapper.selectByFloorAndBuild(Integer.parseInt(floor),buildingid);
        Exhibition nearbyExhibition = ExhibitionUtil.NearbyExhibition(exhibitions, jsonBody, RedisUtil.USER_EXHIBITION_DISTANCE);
        String visitedEx = RedisUtil.hget(RedisUtil.ACTIVE_USER_EXHIBIT, userId);
        Integer eId = -1;
        Integer nearEId = -1;
        //设置时间为一个很远的
        Date lastVisitTime = new Date(1L);
        if( visitedEx != null){
            JSONObject jsonVisit = JSONObject.parseObject(visitedEx);
            eId = jsonVisit.getInteger("eId");
            lastVisitTime = jsonVisit.getDate("sTime");
        }
        if(nearbyExhibition != null){
            nearEId = nearbyExhibition.getExhibitionId();
        }
        if(eId !=  nearEId && timestamp.compareTo(lastVisitTime) > 0){
            if(nearbyExhibition != null){//到达一个展品
                jsonBody.put("exhibition",JSONObject.toJSONString(nearbyExhibition));
                JSONObject exhibitionJson = new JSONObject();
                exhibitionJson.put("eId",nearbyExhibition.getExhibitionId());
                exhibitionJson.put("sTime",timestamp);
                RedisUtil.hset(RedisUtil.ACTIVE_USER_EXHIBIT,userId,exhibitionJson.toJSONString());
            }else{//离开了上一个展品
//                jsonBody.put("exhibition","");
                RedisUtil.hdel(RedisUtil.ACTIVE_USER_EXHIBIT,userId);
            }
            //如果用户此前有没存库的参观展品记录就存库
           if(lastVisitTime.compareTo(new Date(1L)) != 0){
               ExhibitionVisitInfo exhibitionVisitInfo = new ExhibitionVisitInfo(eId,lastVisitTime,timestamp,2);
//                    ExhibitionVisitInfo exhibitionVisitInfo = new ExhibitionVisitInfo(eId,lastVisitTime,timestamp,userId);
               exhibitionVisitInfoMapper.insert(exhibitionVisitInfo);
           }
        }
        return jsonBody.toJSONString();
    }


    //每五分钟把redis中的游客数量统计信息存库清除
    @Scheduled(cron = " 0 0/1 0-23 * * ?")
    public void savePersonNum(){
        Date now = new Date(System.currentTimeMillis());
        //取出redis中的人数统计信息 存入数据库
        List<String> keys = RedisUtil.hkeys(RedisUtil.PERSON_NUMBER);
        if(keys != null){
            //各类用户数量存库
            log.info("用户数量统计开始存库");
            for (String key : keys) {
                JSONObject numbers = JSONObject.parseObject(RedisUtil.hget(RedisUtil.PERSON_NUMBER, key));
                String buildingId = key.substring(0, key.indexOf('-'));
                Integer floor = Integer.parseInt(key.substring(key.indexOf('-')+1));
                for (String type : numbers.keySet()) {
                    int num = (int) numbers.get(type);
                    UserNumInfo userNumInfo = new UserNumInfo(buildingId,floor,type,now,num);
                    userNumInfoMapper.insert(userNumInfo);
                }
            }
        }
    }
    //定时每五分钟把redis中的非活跃用户的信息和轨迹存库
    @Scheduled(cron = " 0 0/1 0-23 * * ?")
    public void saveUnactiveUserLoc(){
        long now = System.currentTimeMillis();
        List<String> userIds = RedisUtil.hkeys(RedisUtil.ACTIVE_USER_FLOOR);
        for (String userId : userIds) {
            //查找频次
            Integer freq = locInfoMapper.selectMaxfreqByUserId(2);
//          Integer freq = locInfoMapper.selectMaxfreqByUserId(Integer.parseInt(userId));
            log.info("获取用户频次");
            //活跃用户的定位个数
            Long len = RedisUtil.llen(userId);
            JSONObject lastLoc = JSONObject.parseObject(RedisUtil.lindex(userId,len-1));
            //超过五分钟没有定位就判定用户此次定位结束
            if((now - lastLoc.getLong("locationTime"))/1000/60 > RedisUtil.LEAVE_TIME){
                List<String> locs = RedisUtil.lrange(userId, 0L, len-1);
                //删除redis中的定位信息
                RedisUtil.ltrim(userId,1,0);
                RedisUtil.hdel(RedisUtil.ACTIVE_USER_TYPE,userId);
                RedisUtil.hdel(RedisUtil.ACTIVE_USER_FLOOR,userId);
                RedisUtil.hdel(RedisUtil.ACTIVE_USER_EXHIBIT,userId);
                //存库
                for (String loc : locs) {
                    LocInfo locInfo = JSONObject.toJavaObject(JSONObject.parseObject(loc), LocInfo.class);
                    if(freq == null){
                        locInfo.setFrequency(1);
                    }else{
                        locInfo.setFrequency(freq+1);
                    }
                    locInfo.setUserId(2);
                    locInfoMapper.insert(locInfo);
                }
            }
        }

    }

}
