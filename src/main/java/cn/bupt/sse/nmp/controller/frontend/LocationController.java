package cn.bupt.sse.nmp.controller.frontend;

import cn.bupt.sse.nmp.result.CodeMsg;
import cn.bupt.sse.nmp.result.Result;

import cn.bupt.sse.nmp.service.LocationService;
import cn.bupt.sse.nmp.util.HttpUtils;

import cn.bupt.sse.nmp.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
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
@Api(tags = "定位管理")
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

    @ApiOperation("处理app定位请求")
    @PostMapping(value = "/req")
    public void LocRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        //转发定位请求到定位服务器
        String body = HttpUtils.sendToHuaweiServer(request,address);
        JSONObject jsonBody = JSONObject.parseObject(body);
        if(!jsonBody.getJSONObject("result").getString("message").equals("success")){
            HttpUtils.outResult(response, body);
        }else{
            //判断是否到达新的展品附近
            body = locationService.findExhibition(jsonBody);
            //返回给app定位结果
            HttpUtils.outResult(response, body);
            //修改redis中的客户的相关信息，并且存储 用户身份类别“usertype” 身边的需要提醒的展品信息exhibition
            body = locationService.saveToRedis(body);

        }


    }




}
