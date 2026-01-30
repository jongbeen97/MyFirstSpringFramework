package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
// jsp&servlet에서 사용하는 방식
public class JdbcMemberRepository implements MemberRepository {
    // DB에 연결하기 위한정보를 담고있는 객체 주입
    @Autowired
    private DataSource dataSource;

    // 생성자 의존성 주입
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        // ? ( 바인딩 ) : 바인딩 변수 -> 나중에 실제 데이터가 들어갈 자리 표시
        String sql = "INSERT INTO member(name) VALUES(?)";

        Connection conn = null; //DB와 연결하는 객체 (다리의 역할)
        PreparedStatement pstmt = null; // SQL 쿼리 문을 실행하는 객체
        ResultSet rs = null; // SQL문의 결과 값을 돌려받는 객체

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql); //SQL문을 실행되는 곳. 여기서 SQL문 실행되도록 할 것
            pstmt.setString(1, member.getName()); //첫번째 물음표에 이름을 집어 넢겟다는 것이다.

            pstmt.executeUpdate(); // 실제로 DB에 쿼리를 전달 하는 것. (실행하는 역할을 함)
            rs = pstmt.getGeneratedKeys(); // 방금 생성된 ID(PK)를 가져옴.

            if (rs.next()) { // 쿼리 결과 값이 존재한다면
                member.setId(rs.getLong(1)); // 멤버 아이디 조회 수정
            }else{ // 쿼리 결과 값 없다면
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            //e.printstacktrace : 에러 발생시 어떤 에러 발생했는지 출력후 다음 코드를 진행을 하는 것.그래서 둘다 쓰는 건 괜찮다.
            throw new IllegalStateException(e); // Error 발생시 더 이상의 코드는 진행 불가가 된다.
        }finally{
            // 사용한 자원은 반드시 반납을 해주어야 합니다 ( 즉, open -> close) 가 필수 입니다.
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM member WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            // 데이터 조회시 executeQuery() 사용하고 ResultSet에 결과 저장.
            rs = pstmt.executeQuery();

            if (rs.next()) { // 결과가 존재한다면
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));

                // Optional 이용시 NullPointerException 발생하지 않는다.
                return Optional.of(member); //결과를 Optional로 감싸서 반환
            } else {
                return Optional.empty(); // 결과가 없으면 빈 Optional로 반환
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "SELECT * FROM member WHERE name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));

                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();

            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // (AI 수정) 회원 삭제 기능 구현
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM member WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    private Connection getConnection() {
        // 스프링 프레임워크 사용할 때 DataSourceUtils을 사용해야
        // 트랜젝션이 꼬이지 않고 Connection 객체 얻어올 수 있다.
        // 트랜젝션이란? 쪼갤수 없는 가장 작은 논리적인 작업단위 ( 내 마일리지가 구매를 누를 때 마일리지 쌓이는 것)
        // 트랜잭션(작업 단위 관리)
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            if(pstmt != null){
                pstmt.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            if(conn != null){
                conn.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private  void close(Connection conn){
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
