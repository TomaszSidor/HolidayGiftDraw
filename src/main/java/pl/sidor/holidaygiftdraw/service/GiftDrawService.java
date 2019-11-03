package pl.sidor.holidaygiftdraw.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.GiftDraw;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.repository.GiftDrawRepository;
import pl.sidor.holidaygiftdraw.repository.HolidayEventRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GiftDrawService {
    @Autowired
    private GiftDrawRepository giftDrawRepository;
    @Autowired
    private HolidayEventRepository holidayEventRepository;

    public List<GiftDraw> getAll (){ return giftDrawRepository.findAll();}

    public List<GiftDraw> getAllByEvent (HolidayEvent holidayEvent) {
        return giftDrawRepository.findAllByHolidayEvent(holidayEvent);
    }

    public void newGiftDraw(HolidayEvent holidayEvent) {
        List<Account> givers = Lists.newArrayList(holidayEvent.getAccountSet());
        List<Account> receivers = Lists.newArrayList(holidayEvent.getAccountSet());
        Account giver;
        Account receiver;
        List<GiftDraw> listOfDraws = new ArrayList<>();
        while (!givers.isEmpty() && !receivers.isEmpty()) {
            giver = givers.get(new Random().nextInt(givers.size()));
            receiver = receivers.get(new Random().nextInt(receivers.size()));

            if (givers.size() == 1 && receivers.size() == 1 && givers.containsAll(receivers)) {
                return;
            }
            if (giver.equals(receiver)) {
                continue;
            }
            GiftDraw giftDraw = new GiftDraw();
            giftDraw.setGiver(giver);
            giftDraw.setReceiver(receiver);
            giftDraw.setHolidayEvent(holidayEvent);

            listOfDraws.add(giftDraw);
            givers.remove(giver);
            receivers.remove(receiver);
        }
        listOfDraws.forEach(giftDraw -> giftDrawRepository.save(giftDraw));
        holidayEvent.setDrawn(true);
        holidayEventRepository.save(holidayEvent);
    }

    @Scheduled(fixedRate = 6000)
    public void checkForValidEvents() {
        List<HolidayEvent> holidayEventList = holidayEventRepository.findAllByIsDrawn(false);
        for (HolidayEvent holidayEvent : holidayEventList) {
            if (holidayEvent.getDrawDate().isBefore(LocalDate.now()) || holidayEvent.getDrawDate().equals(LocalDate.now())) {
                newGiftDraw(holidayEvent);
            }
        }
    }
}
