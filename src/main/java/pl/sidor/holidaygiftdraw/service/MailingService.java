package pl.sidor.holidaygiftdraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailingService {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;


    public void sendEmail(String toWhom, String content, String subject) throws MessagingException {
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setFrom(from);
        message.setTo(toWhom);
        message.setSubject(subject);
        message.setText(content, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendWelcomeEmail(String toWhom){
        Context context = new Context();

        context.setVariable("emailfootercontactemail", "konto.javagda24.sender@gmail.com");
        context.setVariable("emailbodybegin", "Welcome to our application!");
        context.setVariable("emailbodyend", "We are very happy that you "+toWhom+ " are with us!");
        String welcomeMail = springTemplateEngine.process("welcomeMail", context);
        try {
            sendEmail(toWhom,  welcomeMail, "Welcome email.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
