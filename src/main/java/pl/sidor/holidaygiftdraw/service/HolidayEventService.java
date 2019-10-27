package pl.sidor.holidaygiftdraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.repository.AccountRepository;
import pl.sidor.holidaygiftdraw.repository.HolidayEventRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class HolidayEventService {
    @Autowired
    private HolidayEventRepository holidayEventRepository;
    @Autowired
    private AccountRepository accountRepository;
    public void add(HolidayEvent holidayEvent, String username) {
        Account account = accountRepository.findByUsername(username).get();
        holidayEvent.setIdentifier(UUID.randomUUID().toString());
        holidayEvent.getAccountSet().add(account);
        holidayEvent.setAccount(account);
        holidayEventRepository.save(holidayEvent);
    }

    public Set<HolidayEvent> getEventSetByUserName(String username) {
        Account account = accountRepository.findByUsername(username).get();
        Set<HolidayEvent> holidayEventSet =  account.getHolidayEventSet();
        return account.getHolidayEventSet();
    }

    public Optional<HolidayEvent> findById(Long id) {
        return holidayEventRepository.findById(id);
    }

    public void sendInvitation(Long invited_account, Long eventId) {
        HolidayEvent holidayEvent = holidayEventRepository.findById(eventId).get();
        holidayEvent.getAccountSet().add(accountRepository.findById(invited_account).get());
        holidayEventRepository.save(holidayEvent);
    }
}
