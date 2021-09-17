package dev.highright96.chat.v2.controller;

import dev.highright96.chat.v2.domain.Message;
import dev.highright96.chat.v2.domain.MessageRepository;
import dev.highright96.chat.v2.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
public class ChatController {

  private final SimpMessagingTemplate messagingTemplate;

  private final MessageRepository messageRepository;

  @MessageMapping("/chat/enter")
  public void enter(MessageDto message) {
    message.setMessage(message.getRoomId() + "번 채팅방에 참여했습니다.");
    messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
  }

  @MessageMapping("/chat/message")
  public void message(MessageDto msg) {
    messageRepository.save(new Message(null, msg.getWriter(), msg.getRoomId(), msg.getMessage()));
    messagingTemplate.convertAndSend("/sub/chat/room/" + msg.getRoomId(), msg);
  }
}
