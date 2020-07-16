package cn.bupt.sse.nmp.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-04 14:40
 **/
@Slf4j
public class HttpUtils {


    public static String sendToHuaweiServer(HttpServletRequest request, String address) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        JSONObject requestParamJsonObject = getRequestPostBytes(request);
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
        return body;
    }


    //传入原connection 返回取消验证的connection
    public static HttpURLConnection notVarify(HttpURLConnection conn, URL url) throws NoSuchAlgorithmException, IOException, KeyManagementException {
        HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
        SSLContext context = SSLContext.getInstance("SSL");
        context.init(null,new TrustManager[] { new TrustAnyTrustManager() },
                new SecureRandom());
        https.setSSLSocketFactory(context.getSocketFactory());
        https.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn = https;
        return conn;
    }

    //访问https 取消证书校验
    static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            // 直接Pass
            return true;
        }
    }
    static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    /**
     * 解析android定位请求中的指纹数据
     *
     * @param request
     * @return
     * @throws IOException
     */

    public static JSONObject getRequestPostBytes(ServletRequest request) {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        String returnstrString = null;
        JSONObject returnJson = null;
        try {
            byte buffer[] = new byte[contentLength];
            for (int i = 0; i < contentLength;) {
                int readlen = request.getInputStream().read(buffer, i, contentLength - i);
                if (readlen == -1) {
                    break;
                }
                i += readlen;
            }
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            returnstrString = new String(buffer, charEncoding);
            returnJson = JSONObject.parseObject(returnstrString);


        } catch (IllegalStateException e) {
            log.info("[HttpRequesParms getRequestPostBytes IllegalStateException]", e);
        } catch (IndexOutOfBoundsException e) {
            log.info("[HttpRequesParms getRequestPostBytes IndexOutOfBoundsException]", e);
        } catch (NullPointerException e) {
            log.info("[HttpRequesParms getRequestPostBytes NullPointerException]", e);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            log.info("[HttpRequesParms getRequestPostBytes UnsupportedEncodingException]", e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.info("[HttpRequesParms getRequestPostBytes IOException]", e);
        }

        return returnJson;
    }
    public static HttpURLConnection setRequestParameter(Map<String,Object> map,HttpURLConnection conn) throws ProtocolException {
        conn.setConnectTimeout(10000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Content-Type", "application/json");
        for (String s : map.keySet()) {
            conn.setRequestProperty(s,(String)map.get(s));
        }
      return conn;
    }

    /**
     * @desc 获取 response 响应输入流中的数据
     * @param in
     * @return 定位服务器的定位结果
     * @throws IOException
     */
    public static String getResponseString(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
            if (sb.length() >= 100000) {
                throw new IOException("input too long");
            }
        }

        in.close();
        return sb.toString();
    }




    /**
     * @desc 将定位结果写到返回给app的response中
     * @param responce
     * @param jsonStr
     */
    public static void outResult(ServletResponse responce, String jsonStr) {
        if (responce == null) {
            log.info("responce为空");
        } else {
            PrintWriter out = null;
            try {
                responce.setCharacterEncoding("UTF-8");
                responce.setContentType("application/json;charset=utf-8");
                out = responce.getWriter();
                log.info(jsonStr);
                out.write(jsonStr);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }
}
