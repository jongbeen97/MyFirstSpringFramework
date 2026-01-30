package hello.hello_spring.controller;


import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute( "name", "lee");
        return "hello";
    }

    // html 템플릿에 get파라미터 저장(정적 데이터 전달)
    @GetMapping("hello")
    public void hello(Model model) {
        model.addAttribute("name", "lee");

    }

    // html 템플릿에 get파라미터 저장(동적 데이터 전달)
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
//@ResponseBody : http body 부에 직접 데이터 추가, return 값 그대로 반환.
    // @controller 가 아닌 @RestController를 사용하면 하위 메서드에 모두 @responseBody,가 붙은 것과 같은 취급
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name;
    }

    //json 데이터 반환 : 데이터 객체 반환시 기본 json으로 변환, 기본 getter,setter사용
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    //롬복 활용
    //@request에서 name이 매개변수의 이름과 동일하면 @requestParam 생략 가능
    @GetMapping("bye-api")
    @ResponseBody
    public  Bye byeApi(String name){
        Bye bye = new Bye();
        bye.setName(name);
        return bye-api;
    }

    static class hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    @Data // 롬복 어노테이션으로 자동으로 게터 세터 투스트링 모두 작성.
    // 이제부터 필드만 만들고 어네테이션 데이터 만 만들면 된다.
    static class Bye{
        private String name;
    }
}
