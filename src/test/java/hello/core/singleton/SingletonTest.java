package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureCntainer() {
        AppConfig appConfig = new AppConfig();
        //1. 조회: 호출할 때 마다 객체를 생성
        MemoryMemberRepository memoryMemberRepository1 = appConfig.memberRepository();
        //2. 조회: 호출할 때 마다 객체를 생성
        MemoryMemberRepository memoryMemberRepository2 = appConfig.memberRepository();

        //참조값이 다른 것을 확인
        System.out.println("memoryMemberRepository1 = " + memoryMemberRepository1);
        System.out.println("memoryMemberRepository2 = " + memoryMemberRepository2);

        Assertions.assertThat(memoryMemberRepository1).isNotEqualTo(memoryMemberRepository2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();

        System.out.println("instance1 = " + instance1);
        System.out.println("instance2 = " + instance2);

        //isEqulal : 자바의 Equals, 메소드 오버라이드 비교
        //isSameAs : 참조어(인스턴스)를 비교 ==
        Assertions.assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
            AppConfig.class);

        MemberService memoryMemberRepository1 = ac.getBean("memberService", MemberService.class);
        MemberService memoryMemberRepository2 = ac.getBean("memberService", MemberService.class);

        //참조값이 같다! 스프링 빈은 조회 할때마다 처음에 컨테이너에 등록한 객체를 반환한다.
        System.out.println("memoryMemberRepository1 = " + memoryMemberRepository1);
        System.out.println("memoryMemberRepository2 = " + memoryMemberRepository2);

        Assertions.assertThat(memoryMemberRepository1).isSameAs(memoryMemberRepository2);
    }
}
