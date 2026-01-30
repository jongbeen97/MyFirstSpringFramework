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
//jsp&Servlet에서 사용하는 방식
public class JdbcMemberRepository implements MemberRepository{
    // DB의 연결하기 위한 정보를 담고 있는 객체 주입
    @Autowired
    private DataSource dataSource;

    //생성자 의존성 주입
    public JdbcMemberRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO member(name) VALUES(?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,member.getName());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                member.setId(rs.getLong(1));
            }else{
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally{
            close(conn,pstmt,rs);
            }
        }

    }


    @Override
    public Optional<Member> findById(Long id) {
    String sql ="SELECT * FROM member WHERE id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));

                return Optional.of(member);
            }else{
                return Optional.empty();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(conn,pstmt,rs);
        }


    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql ="SELECT * FROM member WHERE name=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);

            rs = pstmt.executeQuery();
            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));

                return  Optional.of(member);
            }else{
                return Optional.empty();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn,pstmt,rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql ="SELECT * FROM member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);


            }else{
                return Optional.empty();
            }v

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(conn,pstmt,rs);
        }
        return List.of();
    }

private Connection getConnection() {
    return DataSourceUtils.getConnection(dataSource);
}

private void close(Connection conn , PreparedStatement pstmt, ResultSet rs){
    try {
        if(rs!=null){
            rs.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    try {
        if(pstmt!=null){
            pstmt.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    try {
        if(conn!=null){
            conn.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    private void close(Connection conn){
        DataSourceUtils.releaseConnection(conn,dataSource);
    }
}
