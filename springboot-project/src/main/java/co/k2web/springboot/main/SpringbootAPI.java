package co.k2web.springboot.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringbootAPI {

    @GetMapping(path = "/test")

    public String testAPI() {
        return "ahihi finish init project";
    }
}
