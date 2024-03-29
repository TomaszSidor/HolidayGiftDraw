package pl.sidor.holidaygiftdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.GiftDraw;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.service.AccountService;
import pl.sidor.holidaygiftdraw.service.GiftDrawService;
import pl.sidor.holidaygiftdraw.service.HolidayEventService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Controller
@RequestMapping(path = "/holidayevent/")
public class HolidayEventController {
    @ModelAttribute("currentUsername")
    public String currentUsername(@AuthenticationPrincipal Principal principal, Model model) {
        if (principal != null){
            model.addAttribute("currentUsername", principal.getName() );
            return  principal.getName();
        }
        return "no name";
    }

    @Autowired
    private HolidayEventService holidayEventService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private GiftDrawService giftDrawService;

    @GetMapping("")
    public String getHolidayEventPage() {
        return "holidayEvent";
    }

    @GetMapping("/add")
    public String getHolidayEventForm() {
        return "holidayEvent-form";
    }

    @PostMapping("/add")
    public String createNewHolidayEvent(HolidayEvent holidayEvent, Principal principal) {
        holidayEventService.add(holidayEvent, principal.getName());
        return "redirect:/holidayevent/list";
    }

    @GetMapping("/list")
    public String getHolidayEventByUserName(Model model, Principal principal) {
        Set<HolidayEvent> holidayEventSet = holidayEventService.getEventSetByUserName(principal.getName());
        model.addAttribute("holidayEvents", holidayEventSet);
        return "holidayEvent-list";
    }

    @GetMapping("/singleView/{id}")
    public String getHolidayEventView(@PathVariable("id") Long id, Model model, Principal principal) {
        Optional<HolidayEvent> holidayEventOptional = holidayEventService.findById(id);
        List<Account> allAccounts = accountService.getAll();
        Account viewer = accountService.getByUsername(principal.getName()).get();
//        List<GiftDraw> allDraws = giftDrawService.getAllByEvent(holidayEventOptional.get());
        model.addAttribute("allAccounts", allAccounts);
        model.addAttribute("viewer", viewer);
        if(holidayEventOptional.isPresent()) {
            model.addAttribute("allDraws", holidayEventOptional.get().getGiftDraws());
            Period period = Period.between(holidayEventOptional.get().getDrawDate(), LocalDate.now());
            model.addAttribute("time_to_draw_days", period.getDays());
            model.addAttribute("time_to_draw_months", period.getMonths());
            model.addAttribute("time_to_draw_years", period.getYears());
            model.addAttribute("holidayEvent", holidayEventOptional.get());
            return "holidayEvent";
        }
        return "redirect:/holidayevent/list";
    }

    @GetMapping("/invitations")
    public String inviteGuests (Long invited_account, Long eventId){
        holidayEventService.sendInvitation(invited_account, eventId);
        return  "redirect:/holidayevent/singleView/" + eventId;
    }

    @GetMapping("/emailInvitation")
    public String getInviteGuestByEmail (String eventUUID, String email, HttpServletRequest request) throws MessagingException {
        holidayEventService.sendEmailInvitation(email, eventUUID, request);
        Long id = holidayEventService.findByIdentifier(eventUUID).get().getId();
        return  "redirect:/holidayevent/singleView/" + id;
}
    @PostMapping("/emailInvitation")
    public String inviteGuestByEmail (String eventUUID, String email, HttpServletRequest request) throws MessagingException {

        holidayEventService.sendEmailInvitation(email, eventUUID, request);
        Long id = holidayEventService.findByIdentifier(eventUUID).get().getId();
        return  "redirect:/holidayevent/singleView/" + id;
    }

}
