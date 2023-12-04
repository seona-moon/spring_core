package hello.core.singleton;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
            TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: A 사용자 10000원 주문
        statefulService1.order("userA", 10000);
        //ThreadB: B 사용자 20000원 주문
        statefulService2.order("userB", 20000);

        //ThreadA: 사용자 A가 주문 금액을 조회
        //같은 객체를 재사용하기 때문에, 중간에 끼어든 B로 인해 price가 달라지게 됨.
        int price = statefulService1.getPrice();
        Assertions.assertThat(price).isNotEqualTo(10000); //20000 출력
    }

    static  class TestConfig{
        @Bean
        public StatefulService statefulServices(){
            return new StatefulService();
        }
    }
}