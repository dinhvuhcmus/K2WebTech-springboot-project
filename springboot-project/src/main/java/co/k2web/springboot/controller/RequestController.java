package co.k2web.springboot.controller;


import co.k2web.springboot.dao.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @RequestMapping(path = "user-request")
    public String request(@RequestBody User user){
        String requestContent = user.getContent();

        
        return requestContent;
    }

}
