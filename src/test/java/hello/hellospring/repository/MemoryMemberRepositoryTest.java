package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach  // 메서드가 동작하고 실행이 끝날 때마다 어떤 동작을 하는 어노테이션. 일종의 "콜백 메서드"
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();  // get 으로 꺼내면 Optional 한번 까서 보낼 수 있음
//        System.out.println("result = " + (result == member));
//        Assertions.assertEquals(member, result); // 위의 코드처럼 비교할 수 있지만 Assert 로 비교할 수도 있음
        assertThat(member).isEqualTo(result);   // Assertions option + enter static import 로 올라감
    }

    @Test
    public void findByname() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get(); // get 으로 꺼내면 Optional 한번 까서 보낼 수 있음

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}
