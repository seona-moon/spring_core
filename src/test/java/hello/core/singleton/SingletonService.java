package hello.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonService {

    //자기 자신을 내부의 private static으로 선언 -> 클래스 레벨에 올라가므로 하나만 생성됨.
    //자바가 실행될 때 static에 대해서 일괄적으로 실행을 함.
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
