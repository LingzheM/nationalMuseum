package cn.bupt.sse.nmp.util;

import cn.bupt.sse.nmp.entity.Exhibition;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-08 09:51
 **/
public class ExhibitionUtil {
    public static Exhibition NearbyExhibition(List<Exhibition> exhibitions, JSONObject locJson, double distance){
        //scale 图片的比例尺
        double scale = Double.parseDouble(locJson.getString("scale"));
        //将实际距离转化为图上像素的大小
        double disToPixel = distance / scale;
        Double x = locJson.getJSONObject("location").getDouble("x");
        Double y = locJson.getJSONObject("location").getDouble("y");

        Exhibition result = null;
        double minDis = Double.MAX_VALUE;
        for (Exhibition exhibition : exhibitions) {
            Double positionX = exhibition.getPositionX();
            Double positionY = exhibition.getPositionY();
            double dis = Math.sqrt((positionX - x) * (positionX - x) + (positionY - y) * (positionY - y));
            if(dis < minDis && dis < disToPixel){
                minDis = dis;
                result = exhibition;
            }
        }
        return  result;
    }
}
