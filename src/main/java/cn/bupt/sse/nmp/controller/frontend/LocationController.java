package cn.bupt.sse.nmp.controller.frontend;

import cn.bupt.sse.nmp.result.CodeMsg;
import cn.bupt.sse.nmp.result.Result;

import cn.bupt.sse.nmp.service.LocationService;
import cn.bupt.sse.nmp.util.HttpUtils;

import cn.bupt.sse.nmp.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.util.JedisURIHelper;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.util.*;

/**
 * @program: nationalMuseum
 * @description:定位相关
 * @author: Ljx
 * @create: 2020-07-02 22:58
 **/
@Slf4j
@RestController
@RequestMapping(value = "/loc")
public class LocationController {
    @Value("${locationserver.url}")
    String address;

    @Autowired
    private LocationService locationService;
    @RequestMapping(value = "/socket/push/{cid}")

    public Result pushToWeb(@PathVariable String cid, String message){
//        try {
////            WebSocketServer.sendInfo(message,cid);
//            return Result.success("");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return Result.error(CodeMsg.USER_NOT_EXIST);
    }

    /**
     * 从app拿到定位请求，之后发送给定位服务器，拿到结果后做处理返回给app
     * @param request
     * @param response
     * @throws IOException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */

    
    @RequestMapping(value = "/req",method = RequestMethod.POST)
    public void LocRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        JSONObject requestParamJsonObject = HttpUtils.getRequestPostBytes(request);
        String key = requestParamJsonObject.getString("appkey");
        String sign = requestParamJsonObject.getString("sign");
        //创建和定位服务器http1.1连接
        URL url = new URL(address);
        HttpURLConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        OutputStreamWriter ow = null;
        //如果访问的是https，取消证书验证
        if (url.getProtocol().toLowerCase(Locale.ROOT).equals("https")) {
            conn = HttpUtils.notVarify(conn,url);
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("key",key);
        map.put("sign",sign);
        //设置请求属性
        HttpUtils.setRequestParameter(map,conn);
        conn.connect();
        //向定位服务器发送定位请求
        out = conn.getOutputStream();
        ow = new OutputStreamWriter(out);
        ow.write(requestParamJsonObject.toString());
        ow.flush();
        //收到定位结果
        in = conn.getInputStream();
        String body = HttpUtils.getResponseString(in);
        //修改redis中的客户的相关信息，并且存储 用户身份类别“usertype” 身边的需要提醒的展品信息exhibition
        body = locationService.saveToRedis(body);
        //判断定位点最近的
        HttpUtils.outResult(response, body);

    }



}
