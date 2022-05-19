package com.redbird.mail.mail;

import com.redbird.mail.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "mails")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User userFrom;
    @ManyToOne
    private User userTo;
    private String fromName;
    private String toName;
    private Boolean fromImportant;
    private Boolean toImportant;
    private String subject;
    private String message;
    private ZonedDateTime date;
}
