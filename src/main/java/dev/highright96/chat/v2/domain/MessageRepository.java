package dev.highright96.chat.v2.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

  List<Message> findAllByChatRoomId(Long chatRoomId);
}
