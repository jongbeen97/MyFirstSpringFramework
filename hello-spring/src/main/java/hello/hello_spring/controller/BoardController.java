package hello.hello_spring.controller;

import hello.hello_spring.domain.Board;
import hello.hello_spring.domain.Member;
import hello.hello_spring.service.BoardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시판 목록
    @GetMapping("/board")
    public String boardList(Model model, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/"; // 로그인 안 했으면 로그인 페이지로
        }
        
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        model.addAttribute("loginMember", loginMember);
        return "board/list";
    }

    // 글쓰기 폼
    @GetMapping("/board/write")
    public String writeForm(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/";
        }
        return "board/writeForm";
    }

    // 글쓰기 처리
    @PostMapping("/board/write")
    public String write(@RequestParam("content") String content, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/";
        }

        Board board = new Board();
        board.setContent(content);
        board.setAuthorId(loginMember.getId());
        board.setAuthorName(loginMember.getName());

        boardService.write(board);
        return "redirect:/board";
    }

    // 글 삭제
    @PostMapping("/board/delete")
    public String delete(@RequestParam("id") Long id, HttpSession session, Model model) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/";
        }

        Optional<Board> boardOptional = boardService.findById(id);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            
            // 관리자이거나, 본인이 쓴 글인 경우 삭제 가능
            if (loginMember.getId() == 0 || board.getAuthorId().equals(loginMember.getId())) {
                boardService.delete(id);
                return "redirect:/board";
            }
        }
        
        // 권한 없음
        model.addAttribute("message", "삭제할 권한이 없습니다.");
        model.addAttribute("searchUrl", "/board");
        return "message";
    }
}
