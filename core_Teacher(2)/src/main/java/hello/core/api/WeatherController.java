package hello.core.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Controller
public class WeatherController {
    // 상수
    private final String REQUEST_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
    private final String API_KEY = "기상청 단기예보 APPKEY";

    // 화면 이동
    @GetMapping("/weather")
    public void getWeather(Model model) {
        // 날씨 정보를 받아서 화면에게 전달
    }

    // JSON 날씨 데이터 반환
    @GetMapping("/api/weather")
    @ResponseBody // View를 찾지 않고 데이터 그 자체를 반환(JSON)
    public String getWeatherApi() {
        // 날씨 API 호출시 필요한 정보들 전달할 메서드 호출
        return callWeatherApi();
    }

    private String callWeatherApi() {
        String queryParams = "?ServiceKey=" + API_KEY
                + "&pageNo=1"
                + "&numOfRows=100"
                + "&dataType=JSON"
                + "&base_date=20260130"
                + "&base_time=0600"
                + "&nx=60"
                + "&ny=127";

        // 스프링에서 제공하는 외부 API 호출 객체
        RestTemplate restTemplate = new RestTemplate();

        try {
            // 주소 문자열을 URI 객체로 변환
            URI uri = new URI(REQUEST_URL + queryParams);
            // 실제 외부 서버로 요청을 보내고 결과를 문자열로 받아옴
            return restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }


    }
}
