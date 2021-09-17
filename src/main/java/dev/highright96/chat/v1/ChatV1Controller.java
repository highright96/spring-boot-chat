package dev.highright96.chat.v1;

import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatV1Controller {

  @GetMapping("/v1/chat")
  public String chat(Model model) {
    String userid = UUID.randomUUID().toString();
    model.addAttribute("userid", userid);
    return "chatSockJS";
  }
}
