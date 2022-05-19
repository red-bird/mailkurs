package com.redbird.mail.mail;

import com.redbird.mail.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<Mail, Long> {
    public List<Mail> findAllByUserTo(User user);
    public List<Mail> findAllByUserFrom(User from);
//    public List<Mail> findAllByUserFromOrUserTo(User from, User to);
}
