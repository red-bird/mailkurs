package com.redbird.mail.auth;

import com.redbird.mail.user.User;
import com.redbird.mail.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationReqDTO req) {
        ResponseEntity<?> authenticate = authenticationService.authenticate(req);
        return authenticate;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        String password = user.getPassword();
        User newUser = userService.saveUser(user);
        ResponseEntity<?> authenticate = authenticate(new AuthenticationReqDTO()
                .setUsername(user.getUsername())
                .setPassword(password));
        return authenticate;
    }
}
