package com.site.service;

import com.site.domain.User;
import com.site.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserMapper userMapper;

    //로그인
    public User login(String id, String password) {
        User user = userMapper.findById(id).orElse(null); // 없는 아이디인 경우 null 반환

        if(user != null && password.equals(user.getPassword())){
            // id가 존재하고 입력한 비밀번호와 DB 비밀번호가 일치
           return user; // User 객체 반환( 로그인 성공 )
        }
        return null; // 로그인 실패 << 미션 >>
    }

    // 회원 가입 비즈니스 로직을 처리하는 메서드
    // 아이디 사용 할수 있는지 없는지 ? 서비스에서 두가지를 동시에 실행하네 ? 중복이 되었어 안되었어 ? 그러면 회원 가입 시켜줄게 , 그러면 회원 가입 안되
    // 이게 하나의 논리적인 작업단위 .
    @Transactional
    public void signUp(User user) {
        if(userMapper.findById(user.getId()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다");
        }
        //아이디가 중복되지 않은 경우
        userMapper.save(user); // 회원 정보를 데이터베이스에 저장
    }
    // 회원 정보 수정 비즈니스 로직을 처리하는 메서드
    @Transactional
    public void modify(User user) {
        userMapper.update(user); // 회원 정보를 데이터베이스에 수정
    }

    // 회원 탈퇴 비즈니스 로직을 처리하는 메서드
    @Transactional
    public void remove(String id) {
        userMapper.deleteById(id); // 회원 정보를 데이터베이스에서 삭제
    }
}
