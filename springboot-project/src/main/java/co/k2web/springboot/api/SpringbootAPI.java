package co.k2web.springboot.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringbootAPI {

    @GetMapping("/test")

    public String testAPI() {
        return "ahihi";
    }
}
