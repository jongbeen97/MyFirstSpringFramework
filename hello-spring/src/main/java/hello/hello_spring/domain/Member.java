package hello.hello_spring.domain;

import com.google.errorprone.annotations.InlineMeValidationDisabled;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

/*
=========================
Component : 자바 빈 등록
Entity : JPA 개체 등록
=========================
 */
@Data
@Entity
@Component
public class Member {

    @Id // 기본키 (PK) 설정)
    // DB가 ID를 자동으로 생성하도록 만들어주는 annotation
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 시스템이 저장하는 id
//    @Column(name = "name") - DB에 컬럼명과 필드명이 같으면 , 어노테이션 생략이 가능하다( 안써도 상관 무)
    private String name;
}
