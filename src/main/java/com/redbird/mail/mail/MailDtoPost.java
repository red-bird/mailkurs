package com.redbird.mail.mail;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MailDtoPost {
    private String from;
    private String to;
    private String subject;
    private String message;
}
