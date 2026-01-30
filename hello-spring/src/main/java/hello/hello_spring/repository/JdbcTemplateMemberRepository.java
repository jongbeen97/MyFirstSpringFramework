package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// 의존성 주입(DI) : final이 붙은 필드를 객체 생성시 자동 주입.
//@RequiredArgsConstructor
@Repository
//@Primary // 여러 후보중 이 객체가 1순위로 주입된다는 의미 .
public class JdbcTemplateMemberRepository implements  MemberRepository{
    // Spring FrameWork가 제공하는 JDBC 이용을 위한 Helper 객체
    // 일반은 커넥션 열어서 쿼리문 다 날리고 커넥션 닫아주고 해야 하지만 JDBC 템플릿 사용하면 내가 요리만 하고 부가적인건 다른 친구들이 도와주는 느낌이다.
    // 즉, DB 사용 코드를 줄이는 기능을 가진 객체
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        //SimpleJdbcInsert : SQL 쿼리를 직접 작성하지 않고 데이터를 넣을 수 있도록 도와주는 객체입니다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        /*
        *************************
        이 방식은 DB가 발급해주는 IDENTITY 값을 그대로 받아오는 것뿐이라서
👉      코드만으로 ‘3부터 다시’ 나오게 만들 수는 없고,
        DB의 IDENTITY 값을 리셋하는 SQL을 실행해야 합니다.
        *******************************
         */
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName()); //컬럼명과 넣을 값을 매핑

        // DB가 자동으로 생성한 ID를 받기
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        //받아서 member 객체에 ID를 저장
        member.setId(key.longValue());

        return member;
    }


    public void resetIdentity(long restartWith){
        jdbcTemplate.update("ALTER TABLE member ALTER COLUMN id RESTART WITH ?",restartWith);
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 쿼리 매서드query  가 조회,연결,반납 모두 자동으로 처리
        List<Member> result = jdbcTemplate.query("SELECT * FROM member WHERE id = ?",memberRowMapper(),id);
        // 결과가 리스트이므로 스트림을 사용하여 findAny()를 통해 리스트에 저장된 첫번째 값을 찾아 반환한다.
        return result.stream().findAny();

    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("SELECT * FROM member WHERE name = ?",memberRowMapper(),name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
       return jdbcTemplate.query("SELECT * FROM member",memberRowMapper());
    }

    public void updateName(Long id,String newName){
        jdbcTemplate.update("UPDATE member SET name = ? WHERE id = ?",newName,id);
    }

//    로우매퍼 : 디비에서 꺼내온 한줄(row) 의 데이터를 자바 객체로 어떻게 저장할지 정의 .

    private RowMapper<Member> memberRowMapper(){
        return (rs,rowNum) ->{
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
    @Override
    public void deleteById(Long id) {
        // JdbcTemplate을 사용하여 DB에서 해당 ID의 데이터를 삭제하는 SQL 실행
        jdbcTemplate.update("delete from member where id = ?", id);
    }
}
