package dev.highright96.chat.v2.controller;

import dev.highright96.chat.v2.domain.User;
import dev.highright96.chat.v2.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
public class UserController {

  private final UserRepository userRepository;

  @GetMapping
  public String createForm() {
    return "createUser";
  }

  @PostMapping("/user/new")
  public String create(@RequestParam String name, RedirectAttributes attr) {
    log.info("{} 유저가 생성되었습니다.", name);
    User user = new User(null, name);
    userRepository.save(user);
    attr.addAttribute("username", user.getName());
    return "redirect:/chat/rooms";
  }
}
