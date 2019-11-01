package pl.sidor.holidaygiftdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.service.AccountService;
import pl.sidor.holidaygiftdraw.service.HolidayEventService;

import javax.mail.MessagingException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Controller
@RequestMapping(path = "/holidayevent/")
public class HolidayEventController {
    @Autowired
    private HolidayEventService holidayEventService;
    @Autowired
    private AccountService accountService;

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
    public String getHolidayEventView(@PathVariable("id") Long id, Model model) {
        Optional<HolidayEvent> holidayEventOptional = holidayEventService.findById(id);
        List<Account> allAccounts = accountService.getAll();
        model.addAttribute("allAccounts", allAccounts);
        if(holidayEventOptional.isPresent()) {
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
    public String getInviteGuestByEmail (String eventUUID, String email) throws MessagingException {
        holidayEventService.sendEmailInvitation(email, eventUUID);
        Long id = holidayEventService.findByIdentifier(eventUUID).get().getId();
        return  "redirect:/holidayevent/singleView/" + id;
}
    @PostMapping("/emailInvitation")
    public String inviteGuestByEmail (String eventUUID, String email) throws MessagingException {

        holidayEventService.sendEmailInvitation(email, eventUUID);
        Long id = holidayEventService.findByIdentifier(eventUUID).get().getId();
        return  "redirect:/holidayevent/singleView/" + id;
    }

}
