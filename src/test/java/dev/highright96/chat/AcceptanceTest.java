package dev.highright96.chat;

import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

  private WebSocketStompClient stompClient;

  @Autowired
  protected SimpMessagingTemplate messagingTemplate;

  protected StompSession session;

  protected BlockingQueue<String> blockingQueue;

  @Autowired
  protected ObjectMapper objectMapper;

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() throws InterruptedException, ExecutionException, TimeoutException {
    RestAssured.port = port;
    stompClient = new WebSocketStompClient(new SockJsClient(
        singletonList(new WebSocketTransport(new StandardWebSocketClient()))));
    blockingQueue = new LinkedBlockingDeque<>();
    session = stompClient.connect("http://localhost:" + port + "/ws",
        new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);
  }

  public class DefaultStompFrameHandler implements StompFrameHandler {

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
      return byte[].class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
      blockingQueue.offer(new String((byte[]) o));
    }
  }

  protected static RequestSpecification given() {
    return RestAssured.given().log().all();
  }
}
