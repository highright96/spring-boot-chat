package dev.highright96.chat;


import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.highright96.chat.v2.dto.MessageDto;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class ChatAcceptanceTest extends AcceptanceTest {

  @TestFactory
  Stream<DynamicTest> manageMessage() {
    Long roomId = 1L;
    enterChatRoom(roomId);
    MessageDto request = new MessageDto(1L, "highright96", "안녕하세요");

    return Stream.of(dynamicTest("메세지를 보내면 메세지를 수신한다.", () -> {
          sendMessage(request);
          sendMessage(request);
          MessageDto response = objectMapper.readValue(blockingQueue.poll(1, SECONDS),
              MessageDto.class);
          assertThat(response.getRoomId()).isEqualTo(request.getRoomId());
          assertThat(response.getMessage()).isEqualTo(request.getMessage());
          assertThat(response.getWriter()).isEqualTo(request.getWriter());
        })
    );
  }

  private void enterChatRoom(Long roomId) {
    session.subscribe("sub/chat/rooms/" + roomId, new DefaultStompFrameHandler());
  }

  private void sendMessage(MessageDto request) throws JsonProcessingException {
    session.send("/pub/chat/message", objectMapper.writeValueAsString(request).getBytes());
  }
}
