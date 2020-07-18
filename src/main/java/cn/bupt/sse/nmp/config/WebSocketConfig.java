package cn.bupt.sse.nmp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpointConfig;

/**
 * @program: nationalMuseum
 * @description:websocket配置类
 * @author: Ljx
 * @create: 2020-07-02 22:34
 **/
//@Configuration
public class WebSocketConfig {
    @Bean
    @Profile(value = {"dev","test"})
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


}

