package com.redbird.mail.mail;

import com.redbird.mail.user.User;
import com.redbird.mail.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MailServiceImpl implements MailService {

    private final UserService userService;
    private final MailRepository mailRepository;

    @Value("${timezone}")
    private String timezone;


    public MailServiceImpl(UserService userService, MailRepository mailRepository) {
        this.userService = userService;
        this.mailRepository = mailRepository;
    }




    @Override
    public Mail getMail(String username, Long id) {
        Optional<Mail> mail = mailRepository.findById(id);
        return checkMail(mail, username);
    }

    @Override
    @Transactional
    public void deleteInputMail(User user, Long id) {
        Optional<Mail> optional = mailRepository.findById(id);
        if (optional.isEmpty()) return;
        try {
            Mail mail = checkMail(optional, user.getUsername());
            if (mail.getUserFrom() != null) {
                optional.get().setUserTo(null);
            }
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Override
    @Transactional
    public void deleteOutputMail(User user, Long id) {
        Optional<Mail> optional = mailRepository.findById(id);
        if (optional.isEmpty()) return;
        try {
            Mail mail = checkMail(optional, user.getUsername());
            if (mail.getUserTo() != null) {
                optional.get().setUserFrom(null);
            }
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Transactional
    @Override
    public Mail changeInputMailImportant(User user, Long id) {
        Mail mail = getMail(user.getUsername(), id);
        mail.setToImportant(!mail.getToImportant());
        return mail;
    }

    @Transactional
    @Override
    public Mail changeOutputMailImportant(User user, Long id) {
        Mail mail = getMail(user.getUsername(), id);
        mail.setFromImportant(!mail.getFromImportant());
        return mail;
    }

    @Override
    @Transactional
    public List<Mail> getInputMails(String username) {
        User user = userService.findByUsername(username);
        return mailRepository.findAllByUserTo(user);
    }

    @Override
    @Transactional
    public List<Mail> getOutputMails(String username) {
        User user = userService.findByUsername(username);
        List<Mail> allByUserFrom = mailRepository.findAllByUserFrom(user);
        return allByUserFrom;
    }

    private Mail checkMail(Optional<Mail> mail, String username) throws IllegalArgumentException {
        if (mail.isPresent()
                && (mail.get().getFromName().equals(username)
                || mail.get().getToName().equals(username))) {
            return mail.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Mail send(String from, String to, String subject, String message) {
        Mail mail = makeSimpleMail(from, to, subject, message);
        return mailRepository.save(mail);
    }

    private Mail makeSimpleMail(String from, String to, String subject, String message) throws IllegalArgumentException{
        User fromUser = userService.findByUsername(from);
        User toUser = userService.findByUsername(to);
        if (toUser == null) {
            throw new IllegalArgumentException();
        }
        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of(timezone));
        Mail mail = Mail.builder().userFrom(fromUser).userTo(toUser)
                .subject(subject).message(message).date(zonedDateTime)
                .fromName(fromUser.getUsername()).toName(toUser.getUsername())
                .toImportant(false).fromImportant(false).build();
        return mail;
    }

}
