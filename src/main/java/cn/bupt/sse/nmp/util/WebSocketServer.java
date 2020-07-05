package cn.bupt.sse.nmp.util;

import cn.bupt.sse.nmp.config.WebSocketConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-02 22:41
 **/
//@Slf4j
//@ServerEndpoint(value = "/websocket/{sid}",configurator = WebSocketConfig.class)
//@Component
//public class WebSocketServer {
//    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//    private static int onlineCount = 0;
//    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
//    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
//
//    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private Session session;
//
//    //接收sid
//    private String sid="";
//
//
//
///**
// * 连接建立成功调用的方法*/
//    @OnOpen
//    public void onOpen(Session session, @PathParam("sid") String sid) {
//        this.session = session;
//        webSocketSet.add(this);     //加入set中
//        addOnlineCount();           //在线数加1
//        log.info("有新窗口开始监听:"+sid+",当前在线人数为" + getOnlineCount());
//        this.sid=sid;
//        try {
//            sendMessage("连接成功");
//        } catch (IOException e) {
//            log.error("websocket IO异常");
//        }
//    }
//

///**
// * 收到客户端消息后调用的方法
// *
// * @param message 客户端发送过来的消息*/
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        log.info("收到来自窗口"+sid+"的信息:"+message);
//        //群发消息
//        for (WebSocketServer item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
///**
// *
// * @param session
// * @param error
// */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        log.error("发生错误");
//        error.printStackTrace();
//    }
//
///**
// * 实现服务器主动推送
// */
//    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
///**
// * 群发自定义消息
// * */
//    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
//        log.info("推送消息到窗口"+sid+"，推送内容:"+message);
//        for (WebSocketServer item : webSocketSet) {
//            try {
//                //这里可以设定只推送给这个sid的，为null则全部推送
//                if(sid==null) {
//                    item.sendMessage(message);
//                }else if(item.sid.equals(sid)){
//                    item.sendMessage(message);
//                }
//            } catch (IOException e) {
//                continue;
//            }
//        }
//    }
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//    public static synchronized void addOnlineCount() {
//        WebSocketServer.onlineCount++;
//    }
//    public static synchronized void subOnlineCount() {
//        WebSocketServer.onlineCount--;
//    }
//    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
//        return webSocketSet;
//    }
//
//
//}
@Slf4j
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static Map<String, List<Session>> electricSocketMap = new ConcurrentHashMap<String, List<Session>>();

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        List<Session> sessions = electricSocketMap.get(userId);
        if(null==sessions){
            List<Session> sessionList = new ArrayList<>();
            sessionList.add(session);
            electricSocketMap.put(userId,sessionList);
        }else{
            sessions.add(session);
        }
        log.error("websocket连接成功");

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId,Session session) {
        if (electricSocketMap.containsKey(userId)){
            electricSocketMap.get(userId).remove(session);
        }
        log.error("websocket关闭连接");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("websocket received message:"+message);
        try {
//            session.getBasicRemote().sendText("这是推送测试数据！您刚发送的消息是："+message);
//            sendMessage(message);
            sendTest();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
       log.error("websocket发生错误");
        error.printStackTrace();
    }

    /**
     * 主动发送
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        for (String s : electricSocketMap.keySet()) {
            List<Session> sessions = electricSocketMap.get(s);
            for (Session session : sessions) {
                session.getBasicRemote().sendText(message+"客户端返回的");
            }
        }
    }
    public void sendTest() throws InterruptedException, IOException {
        Map<String,List<Session>> map = new HashMap<>();
//        for(int i = 0; i < 10; i++){
//            for (String s : electricSocketMap.keySet()) {
//                List<Session> sessions = electricSocketMap.get(s);
//                List<Session> temp = new ArrayList<>();
//                for (Session session : temp) {
//                    session.getBasicRemote().sendText("this is the "+i+"times");
//                    onClose(s,session);
//                }
//            }
//        }
        onClose("1",electricSocketMap.get("1").get(0));
//       onClose("2",electricSocketMap.get("2").get(0));
    }
}

