package com.redbird.mail.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MailDtoGet {

    private Long id;
    private String fromName;
    private String toName;
    private Boolean fromImportant;
    private Boolean toImportant;
    private String subject;
    private String message;
    private ZonedDateTime date;
}
