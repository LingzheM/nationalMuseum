package cn.bupt.sse.nmp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface LocationService {

    String saveToRedis(String body);

    String sendToHuaweiServer(HttpServletRequest request,String address) throws IOException, NoSuchAlgorithmException, KeyManagementException;
}
