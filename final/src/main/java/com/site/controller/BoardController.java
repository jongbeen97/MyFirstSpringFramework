package com.site.controller;

import com.site.domain.Board;
import com.site.domain.User;
import com.site.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 개시글 전체 목록 및 검색 결과를 보여주는 결과 페이지
     * 검색을 해야 한다. 제목 선택 , 내용 선택 , 닉네임 카테고리 ( SearchType )
     * @param searchType : 검색 타입을 줄 것 (제목 , 컨텐츠 , 글쓴이 )
     * @param keyword : 검색 키워드
     * @Param model : ( 화면으로 전달 : 뷰에 데이터를 전달하기 위한 객체 )
     * @return "boards/list"
     */
    @GetMapping
    //@RequestParam : 클라이언트가 전달하는 데이터 name과 변수명이 동일할 경우 자동으로 매핑
    //required = false 이 속성의 경우 parameter로 전달되지 않아도 된다는 의미이다. 에러를 방지한다.
    public String list(@RequestParam(required = false) String searchType,
                       @RequestParam(required = false) String keyword,
                       Model model){
        //전체 게시판 개시글 리스트
        List<Board> boards = boardService.findAll(searchType, keyword);
        model.addAttribute("boards",boards);
        
        // (수정) 검색 파라미터를 모델에 추가
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "boards/list";
    }

    @GetMapping("/{bno}")
    public String detail(@PathVariable long bno, Model model){
//        Board board = BoardService.findById(bno);
//        model.addAttribute("board",board);
        return "boards/detail";
    }

    // 글쓰기 폼으로 이동
    @GetMapping("/write")
    public String writeForm() {
        return "boards/write";
    }
        // 로그인 체크
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("loginUser") == null) {
//            rttr.addFlashAttribute("msg", "로그인이 필요한 서비스입니다.");
//            return "redirect:/users/login";
//        }
//        return "boards/write";
//    }

    // 글 등록 처리
    @PostMapping("/write")
    public String write(Board board, HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null) {
                // 작성자 정보 설정 (로그인한 사용자의 이름 또는 아이디)
                board.setWritter(loginUser.getName()); // 또는 loginUser.getId()
                boardService.save(board);
                rttr.addFlashAttribute("msg", "게시글이 등록되었습니다.");
                return "redirect:/boards";
            }
        }
        rttr.addFlashAttribute("msg", "로그인이 필요한 서비스입니다.");
        return "redirect:/users/login";
    }
}
