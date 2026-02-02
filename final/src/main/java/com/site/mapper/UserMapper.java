package com.site.mapper;

import com.site.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/*
회원 데이터 접근하기 위한 Mybatis 매퍼 인터페이스
여기에서 선언된 메서드 들은 resources/mappers/UserMapper.xml에 정의된 SQL 쿼리와 매핑 처리
@Mapper : 스프링이 인터페이스를 MyBatis Mapper로 인식하고 구현체를 자동으로 생성해주기 위해서
사용을 하는 것이다.
 */

@Mapper
public interface UserMapper {

    // @param id : 조회할 회원이 ID
    // ( 전달받은 아이디를 가지고 조회할 회원의 아이디이다), 조회와 아이디로 회원 조회)
    // @return : 조회된 회원의 정보. Optional 객체 사용을 통해 결과가 Null일 경우 안전하게 처리한다.

    /**
     * 회원조회
     * @param id : 조회할 회원이 ID
     *            ( 전달받은 아이디를 가지고 조회할 회원의 아이디이다), 조회와 아이디로 회원 조회)
     * @return : 조회된 회원의 정보. Optional 객체 사용을 통해 결과가 Null일 경우 안전하게 처리한다.
     */
    Optional<User> findById(String id);

    /**
     * 회원 등록
     * @param user : DB에 저장할 회원 정보 객체
     */
    void save(User user);

    /**
     * 회원 정보 수정
     *  @param user : DB에 수정할 회원 정보 객체
     */
    void update(User user);

    // 회원 탈퇴
    /**
     * 회원 탈퇴
     * @param id : 탈퇴할 회원의 ID
     */
    void deleteById(String id);

}
