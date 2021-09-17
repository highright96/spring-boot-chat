package dev.highright96.chat.v2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDto {

  private Long roomId;
  private String writer;
  private String message;

  public MessageDto(Long roomId, String writer, String message) {
    this.roomId = roomId;
    this.writer = writer;
    this.message = message;
  }
}
