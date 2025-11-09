package com.kodekernel.website.service;

import com.kodekernel.website.model.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Value("${spring.mail.username}")
    private String emailTo;
    @Autowired
    private JavaMailSender mailSender;

    public ResponseEntity<String> processAndSend(EmailDTO email) {
        log.info("Processing query from {} about {}", email.getName(), email.getService());
        try {
            String processedMessage =
                    "Name: " + email.getName() + "\n" +
                            "Email: " + email.getEmail() + "\n" +
                            "Message:\n" + email.getMessage();
            // Send email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(emailTo);
            mailMessage.setSubject("Enquiry: " + email.getService());
            mailMessage.setText(processedMessage);

            mailSender.send(mailMessage);
            log.info("Email sent successfully to {}", emailTo);
            return ResponseEntity.ok("Email delivered successfully.");
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
