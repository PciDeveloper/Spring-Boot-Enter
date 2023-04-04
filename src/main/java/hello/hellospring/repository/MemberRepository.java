package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 회원의 정보를 저장
    // 회원 정보를 찾을 때 id, name 이 없다면 null 이 반환 되므로,
    // null 을 Optional 로 감싸서 반환
    Optional<Member> findById(Long id); // id 정보로 일치하는 회원 찾기
    Optional<Member> findByName(String name);   // name 정보로 일치하는 회원 찾기
    List<Member> findAll(); // 모든 회원의 리스트 찾기

}
