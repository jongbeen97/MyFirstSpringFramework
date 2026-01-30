package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberController {
    // 의존성 주입(DI) : 스프링 컨테이너에 있는 멤버 서비스를 가져와서 자동으로 연결
    @Autowired
    private MemberService memberService; //

    // 회원 가입 폼 화면으로 이동
    @GetMapping("/members/new") //new URL로 가면 회원가입을 할수 있는 기능을 구현할수 있도록 도와줌
    public String createForm() {return "members/createMemberForm";}// 이 URL로 이동할수 있도록 반환을 해줌.

    // 회원 컨트롤러에서 회원을 실제로 등록하는 기능
    @PostMapping("/members/new")
    public String create(Member vo, Model model){
        try {
            // 회원 가입 비지니스 로직 실행
            memberService.join(vo);
            // 홈 화면으로 이동
            return "redirect:/";
        } catch (IllegalStateException e) {
            // (AI 수정) 중복 회원 예외 처리: 알림창 띄우고 메인으로 이동
            model.addAttribute("message", "이미 존재하는 회원입니다.");
            model.addAttribute("searchUrl", "/");
            return "message";
        }
    }

    // 홈 화면으로 이동
    @GetMapping("/members")
    public String allMemberList(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    // (AI 수정) 회원 삭제 기능 추가
    @PostMapping("/members/delete")
    public String delete(@RequestParam("id") Long id) {
        memberService.deleteMember(id);
        return "redirect:/members";
    }
}
