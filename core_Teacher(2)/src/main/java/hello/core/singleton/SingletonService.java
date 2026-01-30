package hello.core.singleton;
/*
싱글톤 패턴(Singleton Pattern)은 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴.
주로 애플리케이션 전체에서 공유해야 하는 객체나, 생성 비용이 큰 객체를 효율적으로 사용하기 위해 적용.
 */
public class SingletonService {

    // 1. static 영역에 객체를 딱 1개만 생성해둔다.
    private static final SingletonService instance = new SingletonService();

    // 2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 처리.
    public static SingletonService getInstance() {
        return instance;
    }

    // 3. 기본 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
