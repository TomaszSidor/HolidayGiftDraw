package pl.sidor.holidaygiftdraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.model.dto.EventCreationRequest;
import pl.sidor.holidaygiftdraw.repository.AccountRepository;
import pl.sidor.holidaygiftdraw.repository.HolidayEventRepository;

import java.util.List;
import java.util.Set;

@Service
public class HolidayEventService {
    @Autowired
    private HolidayEventRepository holidayEventRepository;
    @Autowired
    private AccountRepository accountRepository;
    public void add(HolidayEvent holidayEvent, EventCreationRequest request, String username) {
        Account account = accountRepository.findByUsername(username).get();

        holidayEvent.setId(null);
        holidayEventRepository.save(holidayEvent);
        account.getHolidayEventSet().add(holidayEvent);
        holidayEvent.getAccountSet().add(account);

    }

    public Set<HolidayEvent> getEventSetByUserName(String username) {
        Account account = accountRepository.findByUsername(username).get();

        Set<HolidayEvent> holidayEventSet =  account.getHolidayEventSet();

        return account.getHolidayEventSet();

    }




}
