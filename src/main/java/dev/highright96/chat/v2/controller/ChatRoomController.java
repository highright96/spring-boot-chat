package dev.highright96.chat.v2.controller;

import dev.highright96.chat.v2.domain.ChatRoom;
import dev.highright96.chat.v2.domain.ChatRoomRepository;
import dev.highright96.chat.v2.domain.Message;
import dev.highright96.chat.v2.domain.MessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatRoomController {

  private final ChatRoomRepository chatRoomRepository;

  private final MessageRepository messageRepository;

  @GetMapping("/chat/rooms")
  public String findRooms(@RequestParam String username, Model model) {
    List<ChatRoom> chatRooms = chatRoomRepository.findAll();
    model.addAttribute("username", username);
    model.addAttribute("list", chatRooms);
    return "chatRooms";
  }

  @PostMapping("/chat/room")
  public String createRoom(@RequestParam String name, @RequestParam String username,
      RedirectAttributes attr) {
    chatRoomRepository.save(new ChatRoom(null, name));
    attr.addAttribute("username", username);
    return "redirect:/chat/rooms";
  }

  @GetMapping("/chat/room")
  public String findRoom(@RequestParam Long roomId, @RequestParam String username, Model model) {
    ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();
    List<Message> messages = messageRepository.findAllByChatRoomId(roomId);
    model.addAttribute("messages", messages);
    model.addAttribute("username", username);
    model.addAttribute("room", chatRoom);
    return "chatRoom";
  }
}
