package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // 컴포넌트 스캔 방식 어노테이션 타고 들어가보면 @Component
public class MemberController {

//    private final MemberService memberService = new MemberService();

    private final MemberService memberService;

    // DI 에는 필드 주입, setter 주입, 생성자 주입이 있음.
    // 의존관계가 실행 중에 동적으로 변하는 일은 거의 없으므로 "생성자 주입"을 권장
    @Autowired  // MemberService 를 스프링 컨테이너에 있는 MemberService 를 가져다가 연결해줌 DI
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new") // 조회 할 때에는 Get 방식
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // 등록 할 때에는 Post 방식
    public String create(MemberForm form) {
        Member member = new Member();
        // member 에 setName 해서 MemberForm 에 있는 name 을 가져와서 담음
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        // findMembers() 전체 목록을 가져올 수 있음
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
