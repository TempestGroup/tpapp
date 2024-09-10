package kz.tempest.tpapp.integrations.email.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.mail.SimpleMailMessage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailMessage {
    private String email;
    private String subject;
    private String message;

    public SimpleMailMessage toSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        return simpleMailMessage;
    }
}
