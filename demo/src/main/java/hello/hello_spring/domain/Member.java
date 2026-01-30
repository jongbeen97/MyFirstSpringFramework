package hello.hello_spring.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component // 멤버라는 요소다
public class Member {
    // 시스템이 저장하는 아이디
    // 사용자가 입력을 하지 않는다.

    private Long id; //아이디는 자동으로 부여하는 것
    private String name;
}
