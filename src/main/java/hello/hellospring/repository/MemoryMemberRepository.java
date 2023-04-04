package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    // save 를 할 때 키는 id 라서 Long, 값은 Member
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);   // 시퀀스 증가
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 결과가 없으면 null 이 반환 될 가능성이 있으면 Optional.ofNullable 로 감싸줌
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // member.getName 이 파라미터로 넘어온 name 이랑 같은지 확인
        // 같은 경우에는 필터링 해주고, 하나라도 찾으면 반환 해줌
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        // store 에 있는 values => member 들을 반환 해줌
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
