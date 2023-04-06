package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 스프링 컨테이너와 DB 까지 연결한 통합 테스트

@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행해주는 어노테이션
// 테스트에 @Transactional 어노테이션이 있으면 테스트 시작 전에 트랜잭션을 시작하고,
// 테스트가 완료되면 항상 롤백 해준다. 이렇게 하면 DB 에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
// 테스트를 반복해서 실행할 수 있어서 편한 장점이 있음. 지우는 로직을 따로 만들어 줄 필요가 없다.
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입_join() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then 검증
        Member findMember = memberService.findOne(saveId).get();
        // member 의 이름이 findMember 에 이름과 같은지
        // assertThat static import option + enter
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        try {
//            memberService.join(member2);
//            fail();
//        } catch (Exception e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        // then
    }
}