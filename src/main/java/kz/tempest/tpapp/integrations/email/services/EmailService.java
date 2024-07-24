package kz.tempest.tpapp.integrations.email.services;

import kz.tempest.tpapp.integrations.email.dtos.MailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private JavaMailSender sender;

    public void sendMail(MailMessage mailMessage){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailMessage.getEmail());
        message.setSubject(mailMessage.getSubject());
        message.setText(mailMessage.getMessage());
        sender.send(message);
    }
}
