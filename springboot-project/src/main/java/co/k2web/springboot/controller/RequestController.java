package co.k2web.springboot.controller;


import co.k2web.springboot.dto.User;
import co.k2web.springboot.service.RequestService;
import co.k2web.springboot.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.File;
import java.util.Map;

@RestController
@RequestMapping(path = "/springboot-project/api/v1")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping(path = "/user-request")
    public ResponseEntity<User> request(@Valid @RequestBody User user){

        File requestDataFile = requestService.createFile(Constants.USER_REQUEST_FILE_PATH);

        String requestContent = user.getContent();

        Map<String, String> requestMap = requestService.storeRequest(requestContent);

        requestService.writeFile(requestDataFile, requestMap);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
