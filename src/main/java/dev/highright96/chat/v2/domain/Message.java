package dev.highright96.chat.v2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "message_id")
  private Long id;

  private String senderName;

  private Long chatRoomId;

  private String content;

  public Message(Long id, String senderName, Long chatRoomId, String content) {
    this.id = id;
    this.senderName = senderName;
    this.chatRoomId = chatRoomId;
    this.content = content;
  }
}
