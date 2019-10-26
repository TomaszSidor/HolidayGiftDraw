package pl.sidor.holidaygiftdraw.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.GiftDraw;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.re.GiftDrawRepository;
import pl.sidor.holidaygiftdraw.repository.HolidayEventRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class GiftDrawService {
    @Autowired
    private GiftDrawRepository giftDrawRepository;
    @Autowired
    private HolidayEventRepository holidayEventRepository;

    public void newGiftDraw(HolidayEvent holidayEvent) {
        GiftDraw giftDraw = new GiftDraw();
        List<Account> givers = Lists.newArrayList(holidayEvent.getAccountSet());
        List<Account> receivers = Lists.newArrayList(holidayEvent.getAccountSet());
        Account giver;
        Account receiver;
        while (givers.size() != 0 && receivers.size() != 0) {
            giver = givers.get(new Random().nextInt(givers.size()));
            receiver = receivers.get(new Random().nextInt(receivers.size()));
            if (giver.equals(receiver)) {
                continue;
            }
            giftDraw.setGiver(giver);
            giftDraw.setReceiver(receiver);
            giftDraw.setHolidayEvent(holidayEvent);

            giftDrawRepository.save(giftDraw);
            givers.remove(giver);
            receivers.remove(receiver);
        }
        holidayEvent.setDrawn(true);
        holidayEventRepository.save(holidayEvent);
    }

    @Scheduled(fixedRate = 60000)
    public void checkForValidEvents (){
        List<HolidayEvent> holidayEventList= holidayEventRepository.findAllByIsDrawn(false);
        for (HolidayEvent holidayEvent:holidayEventList) {
        newGiftDraw(holidayEvent);
        }
    }
}
