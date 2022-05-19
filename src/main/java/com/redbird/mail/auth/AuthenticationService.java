package com.redbird.mail.auth;

import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<?> authenticate(AuthenticationReqDTO req);
}
