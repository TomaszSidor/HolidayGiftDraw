package pl.sidor.holidaygiftdraw.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sidor.holidaygiftdraw.HolidaygiftdrawApplication;


import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = HolidaygiftdrawApplication.class)
public class MailingServiceTest {
    @Autowired
    private MailingService mailingService;
    @Test
    public void testSendingEmail() {
        Assert.assertNotNull(mailingService);
        try {
            mailingService.sendEmail("pawel.reclaw@gmail.com", "Siema javagda24!", "Testing email.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSendingTemplateEmail() {
        Assert.assertNotNull(mailingService);
        mailingService.sendWelcomeEmail("pawel.reclaw@gmail.com");
    }
}







