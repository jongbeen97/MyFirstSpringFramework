package com.site.controller;

import com.site.domain.User;
import com.site.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
회원 관련 웹 요청 처리 컨트롤러
@Controller : 이 클래스가 Spring-MVC 의 컨트롤러임을 나타냄
@RequestMapping("/users") : 모든 UserController의 모든 메서드는 (일단 /users가 붙어야 여기 오는 것이다. ) '/users'로 시작하는 URL에 매핑
예시 : /users/signup, /users/login , /users/logout 등등
@RequiredArgsConstructor : `final`이 붙은 객체 의존성 주입 .(DI -> IoC)
 */

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원 가입 폼(View)를 보여주는 메서드
    @GetMapping("/signup")
    public String signUpForm() {
        return "users/signupForm";
    }

    // 회원 가입 처리 메서드
    /*
    HTTP POST 요청으로 '/user/signup' URL 접근시 호출
    폼에서 전송된 파라미터() 로(id,password,name,email)가 자동으로 바인딩 된 User 객체 생성
    전달이 된다. 이 파라미터 전송된 파라미터가 자동으로 바인딩 된다는 것이다. 이 User 객체에 이것이 스프링의 기능이다.
    단, 대신 컬럼명과 객체의 필드명(이름)이 일치해야 한다  (맞춰주어야 자동으로 바인딩이 된다).
     */
    @PostMapping("/signup")
    public String signUp(User user, RedirectAttributes rttr) {
        try {
            // 1. 회원 가입 비지니스 로직 시도(예외 발생 가능)
            userService.signUp(user);
            // 2. 로그인 성공시 msg를 loginFrom 화면에 전달
            rttr.addFlashAttribute("msg", "회원가입이 완료되었습니다.");
            // 3. 회원 가입 성공 시, 로그인 페이지로 이동
            return "redirect:/users/login";
        } catch (IllegalArgumentException e) {
            // 4. 로그인 실패시(중복 아이디) 서비스에서 발생시킨 에러 메시지 화면에 전달.
            rttr.addFlashAttribute("msg", e.getMessage());
            // 5. 회원 가입 폼으로 재이동
            return "redirect:/users/signup";
        }
    }


    @GetMapping("/login")
    public String login() {
        return "users/loginForm";
    }

    @PostMapping("/login")
    public String login(String id, String password, HttpServletRequest request, Model model) { // Model 다음 화면으로 전달하기 위해 필요한 것.
        User user = userService.login(id, password);
        if (user != null) {
            //로그인 성공 : 세션을 생성해서 거기에 사용자 정보를 저장해야 , 제가 계속 종빈님으로 갈 것이다.
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user); // 여기서는 문제점이 있음 . 단점이 존재 : 사용자 정보에 세션이 노출이 됨.
            return "redirect:/";
        } else {
            //로그인 실패 : 에러 메시지 전달
            // [★ 미션 : 아이디와 비밀번호 중 무엇을 틀렷는지 알려주는 기능을 주입하시오]
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "users/loginForm";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 기존에 세션이 존재하지 않으면 null 반환(안 만들게 하는 것)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); //세션을 무효화
        }
        return "redirect:/";
    }

    /*
    요청하신 대로 delete 메서드에 대한 상세한 설명을 주석으로 추가했습니다.
추가된 주석 내용:
1.
메서드 파라미터 설명: HttpServletRequest와 RedirectAttributes가 각각 왜 필요한지 설명했습니다.
2.
세션 처리: request.getSession(false)의 의미와 세션에서 사용자 정보를 가져오는 과정을 설명했습니다.
3.
비즈니스 로직: userService.remove()를 통해 실제 DB 삭제가 일어남을 명시했습니다.
4.
후처리: 탈퇴 후 session.invalidate()로 로그아웃 처리하는 이유와 rttr.addFlashAttribute로 메시지를 전달하는 과정을 설명했습니다.
이제 코드를 보실 때 각 줄이 어떤 역할을 하는지 더 쉽게 이해하실 수 있을 것입니다.

    회원 탈퇴 처리 메서드
    1. HttpServletRequest request: 현재 요청 정보를 담고 있는 객체. 세션을 가져오기 위해 사용.
    2. RedirectAttributes rttr: 리다이렉트 시 데이터를 전달하기 위한 객체. 탈퇴 완료 메시지를 전달.
    3. request.getSession(false): 현재 세션을 가져옴. false는 세션이 없으면 새로 생성하지 않고 null을 반환하라는 의미.
    4. session.getAttribute("loginUser"): 세션에 저장된 로그인 사용자 정보(User 객체)를 가져옴.
    5. userService.remove(user.getId()): 서비스 계층을 호출하여 DB에서 해당 ID의 회원 정보를 삭제.
    6. session.invalidate(): 회원 탈퇴 후 로그아웃 처리를 위해 세션을 무효화(삭제).
    7. rttr.addFlashAttribute(...): 리다이렉트된 페이지에서 한 번만 사용할 수 있는 데이터를 저장.
     */

    // 회원 탈퇴 기능
    @GetMapping("/delete")
    public String delete(HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("loginUser");
            if (user != null) {
                userService.remove(user.getId());
                session.invalidate(); // 세션 무효화 (로그아웃 처리)
//                rttr.addFlashAttribute("msg", "회원탈퇴가 완료되었습니다.");
            }
        }
        return "redirect:/";
    }

    //회원 정보 수정 기능
    @GetMapping("/modify")
    public String modifyForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("loginUser");
            if (user != null) {
                model.addAttribute("user", user);
                return "users/modifyForm";
            }
            return "redirect:/";

        }
        return "redirect:/";
    }
}


