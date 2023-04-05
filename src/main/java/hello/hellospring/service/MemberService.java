package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/* MemberService 비지니스 로직 메서드 구현 */
/* test 코드 실행하고 싶을 때 command + shift + t 를 하면
   해당 클래스에 있는 메서드를 선택하여 자동으로 테스트 파일을 만들어 줌 */
//@Service // 컴포넌트 스캔 방식 어노테이션 타고 들어가보면 @Component

public class MemberService {

    // 현재 MemberService 에서 사용하는 new MemoryMemberRepository 와,
    // Test 에서 작성되는 new MemoryMemberRepository 랑 다른 레포지토리임, 다른 인스턴스임
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 따라서 같은 레포지토리를 사용하게 끔 하기 위해서는
    // 이와 같이 외부에서 넣어주도록 변경
    private final MemberRepository memberRepository;

//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원 가입 */
    public Long join(Member member) {
        // 같은 이름의 중복 회원 x
        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);  // save 호출
        return member.getId();  // id 반환
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())    // null 가능성이 있으면 Optional 로 한번 감싸주기
                .ifPresent(m -> { // m(member)에 값이 있으면
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /* 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* 회원 id 조회 */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
