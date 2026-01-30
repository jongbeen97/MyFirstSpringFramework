package hello.hello_spring.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 ID

    @Column(nullable = false, length = 4000)
    private String content; // 게시글 내용

    private Long authorId; // 작성자 ID (Member의 ID)
    private String authorName; // 작성자 이름

    @CreationTimestamp
    private LocalDateTime createdAt; // 작성 시간
}
