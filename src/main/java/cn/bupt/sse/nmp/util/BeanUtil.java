package cn.bupt.sse.nmp.util;

import cn.bupt.sse.nmp.entity.Exhibition;
import cn.bupt.sse.nmp.entity.LocInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-09 14:31
 **/
public class BeanUtil {
    //吧返回app的json变形转换为locInfo实体
    public static LocInfo locJsonToLocInfo(JSONObject json){
        Integer userId = json.getInteger("userId");
        Date timestamp = new Date(json.getLong("timestamp"));
        JSONObject location = json.getJSONObject("location");
        Double locationX = location.getDouble("x");
        Double locationY = location.getDouble("y");
        Integer floor = location.getInteger("z");
        String buildingid = json.getString("buildingid");
        String type = json.getString("usertype");
        Integer exhibitionId = null;
        if(json.containsKey("exhibition")){
           exhibitionId = JSONObject.toJavaObject(JSONObject.parseObject(json.getString("exhibition")), Exhibition.class).getExhibitionId();
        }
        JSONObject gpsLocation = json.getJSONObject("gpslocation");
        Double gpsX = gpsLocation.getDouble("x");
        Double gpsY = gpsLocation.getDouble("y");

        LocInfo locInfo = new LocInfo(userId, timestamp, locationX, locationY, gpsX, gpsY, floor, buildingid, exhibitionId, type);
        return locInfo;
    }

}
