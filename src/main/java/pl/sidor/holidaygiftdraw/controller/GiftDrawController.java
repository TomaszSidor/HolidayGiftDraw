package pl.sidor.holidaygiftdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.GiftDraw;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.service.GiftDrawService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path ="/giftdraw")
public class GiftDrawController {
    @ModelAttribute("currentUsername")
    public String currentUsername(@AuthenticationPrincipal Principal principal, Model model) {
        if (principal != null){
            model.addAttribute("currentUsername", principal.getName() );
            return  principal.getName();
        }
        return "no name";

    }

    @Autowired
    private GiftDrawService giftDrawService;

    @PostMapping("/post")
    public void newGiftDraws (HolidayEvent holidayEvent){
        giftDrawService.newGiftDraw(holidayEvent);
    }


}
