package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/admin/home")
    public String home(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        
        // 관리자(id=0)가 아니면 로그인 페이지로
        if (loginMember == null || loginMember.getId() != 0) {
            return "redirect:/";
        }
        
        return "home";
    }
}
