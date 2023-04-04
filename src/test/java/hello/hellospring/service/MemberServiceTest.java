package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // 현재 MemberService 에서 사용하는 new MemoryMemberRepository 와,
    // Test 에서 작성되는 new MemoryMemberRepository 랑 다른 레포지토리임, 다른 인스턴스임
    // 따라서 같은 레포지토리를 사용하게 끔 하기 위해서는
    // 이와 같이 외부에서 넣어주도록 변경
//    MemberService memberService = new MemberService();
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach // 동작하기 전에 넣어줘야 함
    public void beforeEach() {
        // 같은 MemoryMemberRepository 를 사용하기 위함
        // MemberService 입장에서는 직접 new 하지 않고 외부에서 주입 해주는 방식
        // DI (Dependency Injection) 의존성 주입
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach  // 메서드가 동작하고 실행이 끝날 때마다 어떤 동작을 하는 어노테이션. 일종의 "콜백 메서드"
    public void afterEach() {
        // callback 메서드가 실행이 끝날 때마다 clear 동작
        memberRepository.clearStore();
    }

    @Test
    void 회원가입join() {
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

    @Test
    void findMembers() {

    }

    @Test
    void findOne() {

    }
}