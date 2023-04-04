package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 정적 컨텐츠 = 파일을 그대로 내려준다.
// mvc, template 엔진 = 모델 뷰 컨트롤러로 쪼개서 뷰를 한글로 렌더링 후 html 으로 클라이언트에 전달해준다.

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);   // 파라미터로 넘어온 name 을 넘김
        return "hello-template";
        // http://localhost:8080/hello-mvc?name=spring!!!
    }

    @GetMapping("hello-string")
    @ResponseBody   // http 에서 응답 body 부분에 데이터를 직접 넣어주겠다는 의미
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name;
        // http://localhost:8080/hello-string?name=spring!!!
    }

    // 키 벨류로 이루어진 JSON 방식
    // 요즘은 XML 방식보다는 JSON 방식을 더 선호함
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {

        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    // mac getter setter 단축키 command + n
    static class Hello {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
