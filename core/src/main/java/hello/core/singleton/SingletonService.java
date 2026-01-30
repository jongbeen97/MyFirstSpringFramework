package hello.core.singleton;

public class SingletonService {

    /****************************************************************************************************
    싱글톤 패턴
     **싱글톤 패턴(Singleton Pattern)**은 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴입니다.
    주로 애플리케이션 전체에서 공유해야 하는 객체나 생성비용이 큰 객체를 효율적으로 사용하기 위해 적용합니다.
    생성할 때마다 메모리 공간을 할당받는 것이 낭비일 때, 혹은 전역적으로 공유해야 하는 리소스가 있을 때 사용합니다.
     ****************************************************************************************************/

    // 1. static 영역에 객체를 딱 1개만 생성해둔다.
    private static final SingletonService instance = new SingletonService();

    // 2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 처리.
    public static SingletonService getInstance() {
        return instance;
    }

    // 3. 기본생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService() {
    }
    // 로직은 생성을 할수 있는데 새롭게 생성할수는 없는 것이다.
    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
