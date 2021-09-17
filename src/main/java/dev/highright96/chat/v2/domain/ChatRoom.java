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
public class ChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_room_id")
  private Long id;

  private String name;

  public ChatRoom(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  /*
  @ManyToOne
  @JoinColumn(name = "user_one_id")
  private User user1;

  @ManyToOne
  @JoinColumn(name = "user_two_id")
  private User user2;
  */
}
