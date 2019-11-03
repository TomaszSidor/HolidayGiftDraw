package pl.sidor.holidaygiftdraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.repository.AccountRepository;
import pl.sidor.holidaygiftdraw.repository.HolidayEventRepository;

import javax.mail.MessagingException;
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

    public void sendEmailInvitation(String email, String eventUUID) throws MessagingException {
        HolidayEvent holidayEvent = holidayEventRepository.findByIdentifier(eventUUID).get();
        String URL = "http://localhost:8080/register/" + eventUUID;
        mailingService.sendEmail(email, "Hello this is a invitation link: " + URL + " to application " +
                "HolidayGiftDraw. Click to register ", "Invitation to HolidayGiftDraw");

    }
}
