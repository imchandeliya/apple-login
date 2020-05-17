package com.giddh.appleLogin;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private final AppleLoginUtil appleLoginUtil;

    public LoginController(AppleLoginUtil appleLoginUtil) {
        this.appleLoginUtil = appleLoginUtil;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/login-by-apple")
    public ResponseEntity<String> loginByApple(@RequestBody(required = true) AppleUser appleUser,
                                               HttpServletRequest request) throws IOException, UnirestException {

        String response = appleLoginUtil.appleAuth(appleUser.getAuthorizationCode());
        return new ResponseEntity<String>("kjahsfkj", HttpStatus.OK);
    }
}
