package co.k2web.springboot.controller;

import co.k2web.springboot.dto.User;
import co.k2web.springboot.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/springboot-project/api/v1")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping(path = "/user-request")

    //Add @Valid before @RequestBody to validate field content not empty
    public ResponseEntity<User> request(@RequestBody User user) {
        requestService.handleRequest(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
