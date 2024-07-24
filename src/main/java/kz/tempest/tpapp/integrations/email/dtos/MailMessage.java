package kz.tempest.tpapp.integrations.email.dtos;

import lombok.Data;

@Data
public class MailMessage {
    private String email;
    private String subject;
    private String message;
}
