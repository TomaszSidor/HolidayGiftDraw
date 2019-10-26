package pl.sidor.holidaygiftdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sidor.holidaygiftdraw.model.Account;
import pl.sidor.holidaygiftdraw.model.HolidayEvent;
import pl.sidor.holidaygiftdraw.model.dto.EventCreationRequest;
import pl.sidor.holidaygiftdraw.service.HolidayEventService;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping(path ="/holidayevent/")
public class HolidayEventController {
    @Autowired
    private HolidayEventService holidayEventService;

    @GetMapping ("")
    public String getHolidayEventPage(){ return "holidayEvent";}

    @GetMapping("/add")
    public String getHolidayEventForm(){
        return "holidayEvent-form";}

    @PostMapping("/add")
    public String createNewHolidayEvent(HolidayEvent holidayEvent, EventCreationRequest request, Principal principal){
        holidayEventService.add(holidayEvent, request, principal.getName());
        return "redirect:/holidayevent/list";
    }

    @GetMapping("/list")
    public String getHolidayEventByUserName(Model model, Principal principal){

        Set<HolidayEvent> holidayEventSet = holidayEventService.getEventSetByUserName(principal.getName());
        model.addAttribute("holidayEvents", holidayEventSet);
        return "holidayEvent-list";
    }

    @GetMapping("/guest/invite")
    public String inviteGuestForEvent (Account account){
        holidayEventService.sendInvitacionToAccount(account);
        return "redirect:"
    }

}
