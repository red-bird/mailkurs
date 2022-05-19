package com.redbird.mail.auth;

import com.redbird.mail.security.JwtTokenProvider;
import com.redbird.mail.user.User;
import com.redbird.mail.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticationReqDTO req) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            User user = userRepository.findByUsername(req.getUsername());
            String token = jwtTokenProvider.createToken(req.getUsername());
            Map<Object, Object> res = new HashMap<>();
            res.put("username", req.getUsername());
            res.put("token", token);
            res.put("id", user.getId());
            res.put("roles", user.getRoles());

            return ResponseEntity.ok(res);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination or banned", HttpStatus.FORBIDDEN);
        }
    }
}
