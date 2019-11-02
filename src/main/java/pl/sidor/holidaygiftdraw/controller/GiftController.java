package pl.sidor.holidaygiftdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sidor.holidaygiftdraw.model.Gift;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.service.AccountService;
import pl.sidor.holidaygiftdraw.service.GiftService;
import pl.sidor.holidaygiftdraw.service.HolidayEventService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/gift")
public class GiftController {
    @Autowired
    private GiftService giftService;
    private Principal principal;
    private Model model;
    @Autowired
    private HolidayEventService holidayEventService;
    @Autowired
    private AccountService accountService;

    @GetMapping("")
    public String getGiftsList(Principal principal, Model model) throws RuntimeException{
        this.principal = principal;
        this.model = model;
        try {
            List<Gift> giftsList = giftService.getAllByUserName(principal.getName());
            model.addAttribute("gifts", giftsList);
        } catch (RuntimeException re){
            return "redirect:/gift/add";
        }
        return "gift-list";
    }

    @GetMapping("/price/{eventId} + '/' + {accountId}")
    public String getGiftListByPrice(@PathVariable("eventId") Long eventId, @PathVariable("accountId") Long accountId, Model model){
        Optional<HolidayEvent> holidayEventOptional = holidayEventService.findById(eventId);

        List<Gift> giftListByPrice = giftService.getAllUnderMaxPrice(accountService.getById(accountId).get().getUsername(), holidayEventOptional.get().getGiftMaxPrice());
        model.addAttribute("giftsByPrice", giftListByPrice);
        return "gift-list";
    }



    @GetMapping("/add")
    public String showGiftForm (){
        return "gift-form";
    }
    @PostMapping("/add")
    public String addNewGift (Gift gift, Principal principal){
        giftService.addGift(gift, principal.getName());
        return "redirect:/gift";
    }

    @GetMapping("/edit")
    public String showEditGiftForm () {return "editGift-form";}
    @PostMapping("/edit")
    public String editGift (Gift gift) {
        giftService.editGift(gift);
        return "redirect:/gift";
    }
}
