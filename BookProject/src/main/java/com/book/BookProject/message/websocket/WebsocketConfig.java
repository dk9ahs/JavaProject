package com.book.BookProject.message.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//웹소켓 설정을 위한 자동 빈생성, 소켓활성화, 인수생성자를 위한 어노테이션을 부착한다.
@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    // 웹소켓 메세지를 처리하는 핸들러 선언, 의존성 주입
    WebsocketHandler websocketHandler;

    // 생성자를 통해 WebSocketMessageHandler 인스턴스를 주입받음
    public WebsocketConfig(WebsocketHandler websocketHandler) {
        this.websocketHandler = websocketHandler;
    }

    // 웹소켓 핸들러를 등록하는 메소드를 오버라이드  ws://loaclhost:8080/ws/test 로 연결을 해야한다.
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(websocketHandler, "/test")
                .addInterceptors(new WebsocketHandshakeInterceptor())
                .setAllowedOrigins("*");
    }
}

