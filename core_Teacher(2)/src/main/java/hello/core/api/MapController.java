package hello.core.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
    @GetMapping("/map")
    public String getMap(){
        // 화면 이동
        return "kakaoMap";
    }
}
