package dev.highright96.chat.v1;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
//@Component
public class ChatHandler extends TextWebSocketHandler {

  private List<WebSocketSession> sessionList = new ArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    sessionList.add(session);
    log.info("{} 연결와 연결됐습니다.", session.getId());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    log.info("{}에게 {}를 받았습니다.", session.getId(), message.getPayload());
    for (WebSocketSession sess : sessionList) {
      sess.sendMessage(new TextMessage(message.getPayload()));
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    sessionList.remove(session);
    log.info("{}와 연결이 끊켰습니다.", session.getId());
  }
}
