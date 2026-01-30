package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final MemberService memberService;

    @Autowired
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 로그인 페이지 (첫 화면)
    @GetMapping("/")
    public String loginForm() {
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("id") Long id,
                        @RequestParam("name") String name,
                        HttpSession session,
                        Model model) {
        
        // 관리자 로그인 (id=0, name=관리자)
        if (id == 0 && "관리자".equals(name)) {
            Member admin = new Member();
            admin.setId(0L);
            admin.setName("관리자");
            session.setAttribute("loginMember", admin);
            return "redirect:/admin/home"; // 관리자 홈으로 이동
        }

        // 일반 사용자 로그인
        Optional<Member> memberOptional = memberService.findOne(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (member.getName().equals(name)) {
                session.setAttribute("loginMember", member);
                return "redirect:/board"; // 게시판으로 이동
            }
        }

        // 로그인 실패
        model.addAttribute("error", "ID 또는 이름이 올바르지 않습니다.");
        return "login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
