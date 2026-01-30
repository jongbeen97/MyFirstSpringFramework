package hello.hello_spring.service;

import hello.hello_spring.domain.Board;
import hello.hello_spring.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 작성
    public void write(Board board) {
        boardRepository.save(board);
    }

    // 전체 게시글 조회
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    // 게시글 삭제
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
    
    // 게시글 조회
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }
}
