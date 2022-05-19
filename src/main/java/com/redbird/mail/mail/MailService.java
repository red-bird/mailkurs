package com.redbird.mail.mail;

import com.redbird.mail.user.User;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

public interface MailService {
    Mail send(String from, String emailTo, String subject, String message);
    Mail getMail(String username, Long id);

    void deleteInputMail(User user, Long id);

    void deleteOutputMail(User user, Long id);

    @Transactional
    Mail changeInputMailImportant(User user, Long id);

    @Transactional
    Mail changeOutputMailImportant(User user, Long id);

    List<Mail> getInputMails(String username);
    List<Mail> getOutputMails(String username);
}
