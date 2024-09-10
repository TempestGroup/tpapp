package kz.tempest.tpapp.integrations.email.services;

import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.integrations.email.dtos.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static JavaMailSender sender;

    public EmailService(JavaMailSender sender) {
        EmailService.sender = sender;
    }

    public static boolean send(MailMessage message) {
        try {
            sender.send(message.toSimpleMailMessage());
            return true;
        } catch (Exception e) {
            LogUtil.write(e);
            return false;
        }
    }
}
