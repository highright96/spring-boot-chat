package dev.highright96.chat.v2.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class ChatRoomRequest {

  private String roomId;
  private String name;
  private Set<WebSocketSession> sessions = new HashSet<>();

}
