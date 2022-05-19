package com.redbird.mail.auth;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticationReqDTO {
    private String username;
    private String password;
}
