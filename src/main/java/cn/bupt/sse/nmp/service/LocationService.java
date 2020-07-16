package cn.bupt.sse.nmp.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface LocationService {

    String saveToRedis(String body);

    String findExhibition(JSONObject jsonBody);
}
