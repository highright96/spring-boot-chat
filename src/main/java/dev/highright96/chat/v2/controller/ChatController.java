package dev.highright96.chat.v2.controller;

import dev.highright96.chat.v2.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

  private final SimpMessagingTemplate messagingTemplate;

  /*
  /pub/chat/enter
  */
  @MessageMapping("/chat/enter")
  public void enter(MessageDto message) {
    String msg = message.getRoomId() + "번 채팅방에 참여했습니다.";
    message.setMessage(msg);
    messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
  }

  /*
  /pub/chat/message
  */
  @MessageMapping("/chat/message")
  public void message(MessageDto msg) {
    log.info("{}님이 {}번 채팅방에 {}라는 메세지를 보냈습니다.", msg.getWriter(), msg.getRoomId(), msg.getMessage());
    messagingTemplate.convertAndSend("/sub/chat/room/" + msg.getRoomId(), msg);
  }
}
