package pl.sidor.holidaygiftdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.GiftDraw;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.service.GiftDrawService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path ="/giftdraw")
public class GiftDrawController {
    @Autowired
    private GiftDrawService giftDrawService;

    @PostMapping("/post")
    public void newGiftDraws (HolidayEvent holidayEvent){
        giftDrawService.newGiftDraw(holidayEvent);
    }


}
