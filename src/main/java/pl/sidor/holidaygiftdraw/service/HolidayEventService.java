package pl.sidor.holidaygiftdraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.repository.AccountRepository;
import pl.sidor.holidaygiftdraw.repository.HolidayEventRepository;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class HolidayEventService {
    @Autowired
    private HolidayEventRepository holidayEventRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MailingService mailingService;
    public void add(HolidayEvent holidayEvent, String username) {
        Account account = accountRepository.findByUsername(username).get();
        holidayEvent.setIdentifier(UUID.randomUUID().toString());
        holidayEvent.getAccountSet().add(account);
        holidayEvent.setCreator(account);
        holidayEventRepository.save(holidayEvent);
    }

    public Set<HolidayEvent> getEventSetByUserName(String username) {
        Account account = accountRepository.findByUsername(username).get();

        return account.getHolidayEventSet();
    }

    public Optional<HolidayEvent> findById(Long id) {
        return holidayEventRepository.findById(id);
    }
    public Optional<HolidayEvent> findByIdentifier(String UUID) {
        return holidayEventRepository.findByIdentifier(UUID);
    }

    public void sendInvitation(Long invited_account, Long eventId) {
        HolidayEvent holidayEvent = holidayEventRepository.findById(eventId).get();
        holidayEvent.getAccountSet().add(accountRepository.findById(invited_account).get());
        holidayEventRepository.save(holidayEvent);
    }

    public void sendEmailInvitation(String email, String eventUUID, HttpServletRequest request) throws MessagingException {
        HolidayEvent holidayEvent = holidayEventRepository.findByIdentifier(eventUUID).get();

        String URL = request.getScheme() + "://" + request.getServerName() + "/register?eventUUID=" + eventUUID;
        mailingService.sendEmail(email, "Hello! Welcome to HolidayGiftDraw Application. User " + holidayEvent.getCreator().getUsername() + "is inviting you to event :" + holidayEvent.getName() +  "" +
                        " . If you want to join this event please click that link: " + URL + " and fill out registration form. If you dont know HolidayGiftDraw or "
                        + holidayEvent.getCreator().getUsername() + " please ignore this email. Any questions can be send to info@holidaygiftdraw.pl . Have a nice Holidays!!! ", "Invitation to HolidayGiftDraw");

    }

}
