package com.redbird.mail.mail;

import com.redbird.mail.dto.MailDtoPost;
import com.redbird.mail.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("inbox")
@PreAuthorize("hasAuthority('permission:user')")
public class InboxController {

    private final MailService mailService;

    @Autowired
    public InboxController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("{id}")
    public Mail getMail(Principal principal,
                          @PathVariable Long id) {
        return mailService.getMail(principal.getName(), id);
    }

    @GetMapping
    public List<MailDtoGet> getInputMails(Principal principal) {
        return mailService.getInputMails(principal.getName())
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/output")
    public List<MailDtoGet> getOutputMails(Principal principal) {
        List<Mail> outputMails = mailService.getOutputMails(principal.getName());
        List<MailDtoGet> collect = outputMails.stream().map(this::mapToDto).collect(Collectors.toList());
        return collect;
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<?> deleteInputMail(@AuthenticationPrincipal User user, @PathVariable Long id) {
        mailService.deleteInputMail(user, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/output/{id}")
    public ResponseEntity<?> deleteOutputMail(@AuthenticationPrincipal User user, @PathVariable Long id) {
        mailService.deleteOutputMail(user, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("important/{id}")
    public Mail changeInputMailImportant(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return mailService.changeInputMailImportant(user, id);
    }

    @PutMapping("important/output/{id}")
    public Mail changeOutputMailImportant(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return mailService.changeOutputMailImportant(user, id);
    }

    @PostMapping("/send")
    public ResponseEntity<Mail> sendMail(@RequestBody MailDtoPost mail) {
        if ((!mail.getTo().equals("") || !mail.getFrom().equals(""))
                && (!mail.getSubject().equals("") || !mail.getMessage().equals(""))) {
            try {
                return ResponseEntity.ok().body(mailService.send(mail.getFrom(), mail.getTo(), mail.getSubject(), mail.getMessage()));
            } catch (IllegalArgumentException ignored) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST
                );

            }
        }
        return ResponseEntity.badRequest().build();
    }

    private MailDtoGet mapToDto(Mail mail) {
        MailDtoGet dto = new MailDtoGet();
        dto.setId(mail.getId());
        dto.setFromName(mail.getFromName());
        dto.setToName(mail.getToName());
        dto.setFromImportant(mail.getFromImportant());
        dto.setToImportant(mail.getToImportant());
        dto.setSubject(mail.getSubject());
        dto.setMessage(mail.getMessage());
        dto.setDate(mail.getDate());
        return dto;
    }

}
