package com.site.mapper;

import com.site.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

// board 테이블에 접근하기 위한 마이바티스 매퍼 인터페이스
// 이 인터페이스에 선언된 메서드들은 리소시스에 매퍼에 잇는 XML와 연동되도록 하는 것.
// 'resources/mappers/BoardMapper.xml'에 정의된 SQL 쿼리와 매핑
//@ Mapper : Spring이 이 인터페이스를 MyBatis 매퍼로 인식하고 구현체를 자동으로 생성함.
@Mapper
public interface BoardMapper {

    //개시글 조회
    /**
     * 모든 게시글 또는 검색 결과 조회
     *  * @param searchType : 검색 타입
     *       * @param keyword : 검색키워드
     * @param searchType : 검색 타입(searchType)과 검색 키워드(keyword)가 담긴 맵
     * @return 개시글 목록
     */
    List<Board> findAll(String searchType, String keyword);
}
